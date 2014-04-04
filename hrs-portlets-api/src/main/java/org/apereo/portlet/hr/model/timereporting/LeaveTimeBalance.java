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
 * Model object representing Leave time balance.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class LeaveTimeBalance {
    /**
     * Job code.
     */
    int jobCode;
    /**
     * Time available in minutes
     */
    int timeAvailable;

    public LeaveTimeBalance() {
    }

    public LeaveTimeBalance(int jobCode, int minutes) {
        this.jobCode = jobCode;
        this.timeAvailable = minutes;
    }

    public Integer getJobCode() {
        return jobCode;
    }

    public void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }

    public int getTimeAvailable() {
        return timeAvailable;
    }

    public void setTimeAvailable(int timeAvailable) {
        this.timeAvailable = timeAvailable;
    }
}
