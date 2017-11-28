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

import com.fortify.api.ssc.connection.SSCAuthenticatingRestConnection;

public class SSCAPI extends AbstractSSCAPI {
	private final SSCBugTrackerAPI bugTracker;
	private final SSCCustomTagAPI customTag;
	private final SSCAttributeAPI attribute;
	private final SSCIssueTemplateAPI issueTemplate;
	private final SSCIssueAPI issue;
	private final SSCApplicationVersionAPI applicationVersion;
	private final SSCArtifactAPI artifact;
	private final SSCJobAPI job;
	private final SSCMetricsAPI metrics;
	
	public SSCAPI(SSCAuthenticatingRestConnection conn) {
		super(conn);
		this.bugTracker = new SSCBugTrackerAPI(conn);
		this.customTag = new SSCCustomTagAPI(conn);
		this.attribute = new SSCAttributeAPI(conn);
		this.issueTemplate = new SSCIssueTemplateAPI(conn);
		this.issue = new SSCIssueAPI(conn);
		this.applicationVersion = new SSCApplicationVersionAPI(conn);
		this.artifact = new SSCArtifactAPI(conn);
		this.job = new SSCJobAPI(conn);
		this.metrics = new SSCMetricsAPI(conn);
	}
	
	public final SSCBugTrackerAPI bugTracker() {
		return this.bugTracker;
	}
	
	public SSCCustomTagAPI customTag() {
		return customTag;
	}
	
	public SSCAttributeAPI attribute() {
		return attribute;
	}

	public SSCIssueTemplateAPI issueTemplate() {
		return issueTemplate;
	}

	public SSCIssueAPI issue() {
		return issue;
	}

	public SSCApplicationVersionAPI applicationVersion() {
		return applicationVersion;
	}

	public SSCArtifactAPI artifact() {
		return artifact;
	}
	
	public SSCJobAPI job() {
		return job;
	}
	
	public SSCMetricsAPI metrics() {
		return metrics;
	}

}
