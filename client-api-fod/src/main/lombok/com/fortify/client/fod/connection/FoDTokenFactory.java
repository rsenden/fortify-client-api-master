/*******************************************************************************
 * (c) Copyright 2017 EntIT Software LLC, a Micro Focus company
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
package com.fortify.client.fod.connection;

import java.util.Date;
import java.util.regex.Pattern;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fortify.util.log4j.LogMaskingHelper;

/**
 * This class is used to generate FoD tokens for accessing the
 * FoD REST API. Given an {@link FoDBasicRestConnection} and 
 * authentication form data, it will call the FoD /oauth/token
 * API to request a REST token. The token will be automatically
 * refreshed as required.
 */
public final class FoDTokenFactory {
	static final Log LOG = LogFactory.getLog(FoDTokenFactory.class);
	private static final Pattern EXPR_TOKEN = Pattern.compile("\"access_token\":\"([^\"]+)\"");
	private static final Pattern EXPR_PASSWORD = Pattern.compile("password=([^\\s&]+)");
	
	private final FoDBasicRestConnection basicConn;
	private final Form auth;
	private FoDTokenFactory.TokenData tokenData = null;
	
	public FoDTokenFactory(FoDBasicRestConnection basicConn, Form auth) {
		this.basicConn = basicConn;
		this.auth = auth;
	}

	public String getToken() {
		if ( tokenData == null || tokenData.isExpired() ) {
			LogMaskingHelper.maskByPatternGroups().patterns(EXPR_TOKEN, EXPR_PASSWORD).on(() ->
				tokenData = basicConn.executeRequest(HttpMethod.POST, basicConn.getBaseResource().path("/oauth/token"), Entity.entity(auth, "application/x-www-form-urlencoded"), FoDTokenFactory.TokenData.class)
			);
			LOG.info("[FoD] Obtained access token, expiring at "+new Date(tokenData.getExpiresAt()).toString());
		}
		return tokenData.getAccessToken();
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	private static final class TokenData {
		private String accessToken;
		private long expiresAt;
		public String getAccessToken() {
			return accessToken;
		}
		@JsonProperty("access_token")
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
		@JsonProperty("expires_in")
		public void setExpiresIn(long expiresIn) {
			this.expiresAt = new Date().getTime()+((expiresIn-5)*1000);
		}
		public long getExpiresAt() {
			return expiresAt;
		}
		public boolean isExpired() {
			return new Date().getTime() > expiresAt;
		}
	}
}
