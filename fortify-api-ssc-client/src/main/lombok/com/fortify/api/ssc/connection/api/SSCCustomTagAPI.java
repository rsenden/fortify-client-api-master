/*******************************************************************************
 * (c) Copyright 2017 EntIT Software LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal in the Software without restriction, including without 
 * limitation the rights to use, copy, modify, merge, publish, distribute, 
 * sublicense, and/or sell copies of the Software, and to permit persons to 
 * whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY 
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 ******************************************************************************/
package com.fortify.api.ssc.connection.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;

import com.fortify.api.ssc.connection.SSCAuthenticatingRestConnection;
import com.fortify.api.util.rest.json.JSONList;
import com.fortify.api.util.rest.json.JSONMap;
import com.fortify.api.util.spring.SpringExpressionUtil;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

// TODO Update this class and introduce Query class(es)
public class SSCCustomTagAPI extends AbstractSSCAPI {
	
	public SSCCustomTagAPI(SSCAuthenticatingRestConnection conn) {
		super(conn);
	}
	
	/** Cache for project version custom tags */
	private final LoadingCache<String, JSONList> avCustomTagsCache = CacheBuilder.newBuilder().maximumSize(10)
			.build(new CacheLoader<String, JSONList>() {
				@Override
				public JSONList load(String projectVersionId) {
					return getApplicationVersionEntities(projectVersionId, "customTags");
				}
			});
	
	/** Memoized supplier for custom tags */
	private final Supplier<JSONList> customTagsSupplier = Suppliers.memoize(new Supplier<JSONList>() {
		public JSONList get() { return getEntities("customTags"); };
	});
	
	/**
	 * Set multiple custom tag values for the given collection of vulnerabilities
	 * @param applicationVersionId
	 * @param customTagNamesAndValues
	 * @param vulns
	 */
	public void setCustomTagValues(String applicationVersionId, Map<String,String> customTagNamesAndValues, Collection<Object> vulns) {
		// TODO Simplify this code
		JSONMap request = new JSONMap();
		request.put("type", "AUDIT_ISSUE");

		List<JSONMap> issues = new ArrayList<JSONMap>();
		for ( Object vuln : vulns ) {
			JSONMap issue = new JSONMap();
			issue.put("id", SpringExpressionUtil.evaluateExpression(vuln, "id", Long.class));
			issue.put("revision", SpringExpressionUtil.evaluateExpression(vuln, "revision", Long.class));
			issues.add(issue);
		}
		request.putPath("values.issues", issues);
		JSONList customTagAuditValues = new JSONList(customTagNamesAndValues.size());
		for ( Map.Entry<String, String> customTagNameAndValue : customTagNamesAndValues.entrySet() ) {
			JSONMap customTagAudit = new JSONMap();
			customTagAudit.put("customTagGuid", getCustomTagGuid(customTagNameAndValue.getKey()));
			customTagAudit.put("textValue", customTagNameAndValue.getValue());
			customTagAuditValues.add(customTagAudit);
		}
		request.putPath("values.customTagAudit", customTagAuditValues);
		conn().executeRequest(HttpMethod.POST, 
				conn().getBaseResource().path("/api/v1/projectVersions").path(applicationVersionId).path("issues/action"),
				Entity.entity(request, "application/json"), JSONMap.class);
	}
	
	/**
	 * Set a custom tag value for the given collection of vulnerabilities
	 * @param applicationVersionId
	 * @param customTagName
	 * @param value
	 * @param vulns
	 */
	public void setCustomTagValue(String applicationVersionId, String customTagName, String value, Collection<Object> vulns) {
		Map<String,String> customTagValues = new HashMap<String, String>(1);
		customTagValues.put(customTagName, value);
		setCustomTagValues(applicationVersionId, customTagValues, vulns);
	}
	
	/**
	 * Get the list of custom tag names defined for the given application version
	 * @param applicationVersionId
	 * @return
	 */
	public List<String> getApplicationVersionCustomTagNames(String applicationVersionId) {
		return getCachedApplicationVersionCustomTags(applicationVersionId).getValues("name", String.class);
	}
	
	/**
	 * Get the list of custom tag GUID's defined for the given application version
	 * @param applicationVersionId
	 * @return
	 */
	public List<String> getApplicationVersionCustomTagGuids(String applicationVersionId) {
		return getCachedApplicationVersionCustomTags(applicationVersionId).getValues("guid", String.class);
	}
	
	/**
	 * Get the custom tag GUID for the given custom tag name
	 * @param customTagName
	 * @return
	 */
	public String getCustomTagGuid(String customTagName) {
		return getCachedCustomTags().mapValue("name.toLowerCase()", customTagName.toLowerCase(), "guid", String.class);
	}
	
	/**
	 * Get the custom tag name for the given custom tag GUID
	 * @param customTagGUID
	 * @return
	 */
	public String getCustomTagName(String customTagGUID) {
		return getCachedCustomTags().mapValue("guid", customTagGUID, "name", String.class);
	}
	
	/**
	 * Get a cached JSONList describing all available custom tags defined in SSC
	 * @return
	 */
	public JSONList getCachedCustomTags() {
		return customTagsSupplier.get();
	}
	
	/**
	 * Get a cached JSONList describing all available custom tags for the given application version
	 * @return
	 */
	public JSONList getCachedApplicationVersionCustomTags(String applicationVersionId) {
		return avCustomTagsCache.getUnchecked(applicationVersionId);
	}

}