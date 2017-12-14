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
package com.fortify.api.util.rest.query;

import com.fortify.api.util.rest.connection.IRestConnection;

import lombok.Getter;

/**
 * <p>This abstract class extends {@link AbstractRestConnectionQueryConfig}, adding a
 * property to configure whether caching should be enabled or disabled for the current
 * query.</p>
 * 
 * @author Ruud Senden
 *
 * @param <ConnType> Concrete {@link IRestConnection} type
 * @param <T> Concrete type of this class
 */
@Getter
public abstract class AbstractRestConnectionWithCacheQueryConfig<ConnType extends IRestConnection, T> 
	extends AbstractRestConnectionQueryConfig<ConnType, T> 
{
	private boolean useCache;
	
	protected AbstractRestConnectionWithCacheQueryConfig(ConnType conn, boolean pagingSupported) {
		super(conn, pagingSupported);
	}
	
	public T useCache(boolean useCache) {
		this.useCache = useCache;
		return _this();
	}
	
}