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

package edu.byu.hr.model.timereporting;

import java.io.Serializable;

/**
 * DTO for a Time Punch entry for a particular job code.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class TimePunchEntry implements Serializable {
    JobDescription job;
    // Minutes worked on this job during the current week.
    int weekTimeWorked;
    // Minutes worked on this job during the current pay period
    int payPeriodTimeWorked;
    boolean punchedIn;

    public TimePunchEntry() {
    }

    /**
     * DTO for a job's summary time worked during the week and the pay period, along with an indication of whether
     * the employee is currently punched into the job.
     * @param job Job Description
     * @param weekTimeWorked number of minutes currently worked on this job during this week
     * @param payPeriodTimeWorked number of minutes currently worked on this job during this pay period.
     * @param punchedIn true if the employee is currently punched into this job.
     */
    public TimePunchEntry(JobDescription job, int weekTimeWorked, int payPeriodTimeWorked, boolean punchedIn) {
        this.job = job;
        this.weekTimeWorked = weekTimeWorked;
        this.payPeriodTimeWorked = payPeriodTimeWorked;
        this.punchedIn = punchedIn;
    }

    public JobDescription getJob() {
        return job;
    }

    public void setJob(JobDescription job) {
        this.job = job;
    }

    /**
     * Returns the number of minutes currently worked on this job during this week.
     * @return Number of minutes
     */
    public int getWeekTimeWorked() {
        return weekTimeWorked;
    }

    /**
     * Sets the number of minutes currently worked on this job during this week.
     * @param weekTimeWorked Number of minutes
     */
    public void setWeekTimeWorked(int weekTimeWorked) {
        this.weekTimeWorked = weekTimeWorked;
    }

    /**
     * Returns the number of minutes currently worked on this job during this pay period.
     * @return Number of minutes
     */
    public int getPayPeriodTimeWorked() {
        return payPeriodTimeWorked;
    }

    /**
     * Sets the number of minutes currently worked on this job during this pay period.
     * @param payPeriodTimeWorked Number of minutes
     */
    public void setPayPeriodTimeWorked(int payPeriodTimeWorked) {
        this.payPeriodTimeWorked = payPeriodTimeWorked;
    }

    /**
     * True if employee is currently punched into this job code.
     * @return True if employee is currently punched into this job code.
     */
    public boolean isPunchedIn() {
        return punchedIn;
    }

    /**
     * Sets whether the employee is currently punched into this job code.
     * @param punchedIn True if employee is currently punched into this job code.
     */
    public void setPunchedIn(boolean punchedIn) {
        this.punchedIn = punchedIn;
    }
}
