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

package org.apereo.portlet.hr.timereporting.service;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apereo.portlet.hr.model.timereporting.LeaveSummary;
import org.apereo.portlet.hr.model.timereporting.PayPeriodDailyLeaveTimeSummary;
import org.apereo.portlet.hr.model.timereporting.TimePeriodEntry;
import org.joda.time.LocalDate;

/**
 * Service interface for Staff Time Reporting.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public interface StaffTimeReportingService {

    /**
     * Returns the leave hours reported by the employee starting with the pay period containing
     * <code>dateInPayPeriod</code>.
     * @param request PortletRequest
     * @param emplId Employee ID
     * @param dateInPayPeriod Date within a pay period to return data for
     * @return Summary object holding the leave hours reported by the employee.
     */
    PayPeriodDailyLeaveTimeSummary getLeaveHoursReported(PortletRequest request, String emplId, LocalDate dateInPayPeriod);

    /**
     * Update the leave hours reported by the employee.
     * @param request Portlet Request
     * @param emplId Employee ID
     * @param updatedTimesheet Unordered list of time period entries. Implementations should insure submitted
     *                         entries are valid, employee is allowed to enter or update time for the job code, and
     *                         all entries are within the same time period if appropriate for the use case.  0 values
     *                         occur whether the user entered data or not.
     */
    void updateLeaveTimeReported(PortletRequest request, String emplId, List<TimePeriodEntry> updatedTimesheet);

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

}
