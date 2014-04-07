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

package org.apereo.portlet.hr.dao.timereporting;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apereo.portlet.hr.model.timereporting.LeaveSummary;
import org.apereo.portlet.hr.model.timereporting.PayPeriodDailyLeaveTimeSummary;
import org.apereo.portlet.hr.model.timereporting.TimePeriodEntry;
import org.joda.time.LocalDate;

/**
 * Allows reporting and viewing sick and vacation leave
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public interface StaffTimeReportingDao {

    /**
     * Returns summary of the hours worked and the sick and vacation time recorded for the employee during the
     * pay period that contains the indicated date.
     *
     * @param request Portlet Request
     * @param emplId Employee ID
     * @param dateInPayPeriod date to get pay period data for (may be date within a pay period)
     * @return Summary of hours worked, sick, and vacation time during the pay period
     */
    PayPeriodDailyLeaveTimeSummary getLeaveHoursReported(PortletRequest request, String emplId, LocalDate dateInPayPeriod);

    /**
     * Returns the leave summary (sick, vacation, etc.) for the employee for whatever time period (year to date, quarter to date,
     * month to date, etc.) is appropriate for this institution's needs.
     * needs.
     *
     * @param request Portlet Request
     * @param emplId Employee ID
     * @return Leave summary information for employee.
     */
    LeaveSummary getLeaveSummary(PortletRequest request, String emplId);

    /**
     * Updates the time entries for the employee.
     *
     * @param request Portlet Request
     * @param emplId Employee ID
     * @param updatedTimesheet List of time sheet entries to update.
     */
    void updateLeaveTimeReported(PortletRequest request, String emplId, List<TimePeriodEntry> updatedTimesheet);
}
