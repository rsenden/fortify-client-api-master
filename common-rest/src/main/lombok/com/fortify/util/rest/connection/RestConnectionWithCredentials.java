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
package com.fortify.util.rest.connection;

import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * This {@link AbstractRestConnection} implementation allows for building 
 * authenticating REST connection instances, using the standard 
 * {@link UsernamePasswordCredentials}-based authentication mechanisms 
 * provided by Apache HttpClient (i.e. Basic authentication).
 * 
 * @author Ruud Senden
 *
 */
public class RestConnectionWithCredentials extends AbstractRestConnection {

	protected RestConnectionWithCredentials(AbstractRestConnectionWithCredentialsConfig<?> config) {
		super(config);
	}
	
	public static final RestConnectionWithCredentialsBuilder builder() {
		return new RestConnectionWithCredentialsBuilder();
	}
	
	
	public static final class RestConnectionWithCredentialsBuilder extends AbstractRestConnectionWithCredentialsConfig<RestConnectionWithCredentialsBuilder> implements IRestConnectionBuilder<RestConnectionWithCredentials> {
		@Override
		public RestConnectionWithCredentials build() {
			return new RestConnectionWithCredentials(this);
		}
	}

}
