/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apereo.portlet.hr.model.timereporting;

/**
 * Model of a brief job description (not a full-HR job description with responsibilities).
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class JobDescription {

    /**
     * Job code from HR System
     */
    int jobCode;

    /**
     * Job title
     */
    String jobTitle;

    /**
     * Job description
     */
    String jobDescription;

    public JobDescription() {
    }

    public JobDescription(int jobCode, String jobTitle, String jobDescription) {
        this.jobCode = jobCode;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
    }

    public int getJobCode() {
        return jobCode;
    }

    public void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}
