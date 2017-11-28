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
package com.fortify.api.ssc.connection.api.query;

import javax.ws.rs.client.WebTarget;

import com.fortify.api.ssc.connection.SSCAuthenticatingRestConnection;

public final class SSCApplicationVersionQuery extends AbstractSSCEntityQuery<SSCApplicationVersionQuery> {
	public SSCApplicationVersionQuery(SSCAuthenticatingRestConnection conn) {
		super(conn);
	}

	public SSCApplicationVersionQuery id(String id) {
		return queryAppend("id", id);
	}

	public SSCApplicationVersionQuery applicationName(String applicationName) {
		return queryAppend("project.name", applicationName);
	}

	public SSCApplicationVersionQuery versionName(String versionName) {
		return queryAppend("name", versionName);
	}
	
	@Override
	public SSCApplicationVersionQuery fields(String... fields) {
		return super.fields(fields);
	}
	
	@Override
	public SSCApplicationVersionQuery queryAppend(String field, String value) {
		return super.queryAppend(field, value);
	}
	
	@Override
	public SSCApplicationVersionQuery orderBy(String orderByField) {
		return super.orderBy(orderByField);
	}
	
	@Override
	public SSCApplicationVersionQuery maxResults(int maxResults) {
		return super.maxResults(maxResults);
	}
	
	@Override
	protected boolean isPagingSupported() {
		return true;
	}

	@Override
	protected WebTarget getBaseWebTarget() {
		return conn().getBaseResource().path("api/v1/projectVersions");
	}
}