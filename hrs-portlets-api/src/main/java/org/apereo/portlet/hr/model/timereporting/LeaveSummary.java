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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Summary object holding the leave (sick time, vacation, etc.) earned, taken, and current balance in whatever applicable time period
 * (year to date, quarter to date, month to date, etc.).
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class LeaveSummary {

    /**
     * List of leave job descriptions.  The order of the list determines the order the job descriptions are presented on the UI.  There
     * should only be entries for job codes that are in the other members of this class.
     */
    List<JobDescription> jobDescriptions;

    /**
     * Set of leave balances for the employee.  Order may be used by the UI to determine order presented to the user.
     */
    Set<JobCodeTime> leaveBalance;

    /**
     * Set of leave time earned by the employee in applicable time period.
     */
    Set<JobCodeTime> leaveEarned;

    /**
     * Set of leave time taken by the employee in applicable time period.
     */
    Set<JobCodeTime> leaveTaken;

    public List<JobDescription> getJobDescriptions() {
        return jobDescriptions;
    }

    public void setJobDescriptions(List<JobDescription> jobDescriptions) {
        this.jobDescriptions = jobDescriptions;
    }

    public Set<JobCodeTime> getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Set<JobCodeTime> leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public Set<JobCodeTime> getLeaveEarned() {
        return leaveEarned;
    }

    public void setLeaveEarned(Set<JobCodeTime> leaveEarned) {
        this.leaveEarned = leaveEarned;
    }

    public Set<JobCodeTime> getLeaveTaken() {
        return leaveTaken;
    }

    public void setLeaveTaken(Set<JobCodeTime> leaveTaken) {
        this.leaveTaken = leaveTaken;
    }

    private Map<Integer, Integer> asMap(Set<JobCodeTime> items) {
        HashMap<Integer, Integer> results = new HashMap<Integer, Integer>(items.size());
        Iterator<JobCodeTime> i = items.iterator();
        while (i.hasNext()) {
            JobCodeTime item = i.next();
            results.put(item.getJobCode(), item.getTime());
        }
        return results;
    }

    /**
     * Returns the leave balance as a map <jobCode, timeInMinutes>.
     * @return Leave balance as map.
     */
    public Map<Integer, Integer> getLeaveBalanceAsMap() {
        return asMap(leaveBalance);
    }

    /**
     * Returns the leave earned as a map <jobCode, timeInMinutes>.
     * @return Leave earned as map.
     */
    public Map<Integer, Integer> getLeaveEarnedAsMap() {
        return asMap(leaveEarned);
    }

    /**
     * Returns the leave taken as a map <jobCode, timeInMinutes>.
     * @return Leave taken as map.
     */
    public Map<Integer, Integer> getLeaveTakenAsMap() {
        return asMap(leaveTaken);
    }
}
