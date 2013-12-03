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

package edu.byu.hr.timereporting.service;

import java.util.List;

import javax.portlet.PortletRequest;

import edu.byu.hr.model.timereporting.LeaveTimeBalance;
import edu.byu.hr.model.timereporting.PayPeriodDailyLeaveTimeSummary;
import edu.byu.hr.model.timereporting.TimePeriodEntry;
import org.joda.time.LocalDate;

/**
 * Description
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public interface StaffTimeReportingService {

    PayPeriodDailyLeaveTimeSummary getLeaveHoursReported(PortletRequest request, String emplId, LocalDate dateInPayPeriod);

    void updateLeaveTimeReported(PortletRequest request, String emplId, List<TimePeriodEntry> updatedTimesheet);

    List<LeaveTimeBalance> getLeaveBalance(PortletRequest request, String emplId);
}
