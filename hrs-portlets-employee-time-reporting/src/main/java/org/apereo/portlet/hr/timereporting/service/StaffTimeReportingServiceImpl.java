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

import org.apereo.portlet.hr.dao.timereporting.StaffTimeReportingDao;
import org.apereo.portlet.hr.model.timereporting.LeaveTimeBalance;
import org.apereo.portlet.hr.model.timereporting.PayPeriodDailyLeaveTimeSummary;
import org.apereo.portlet.hr.model.timereporting.TimePeriodEntry;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

@Service
public class StaffTimeReportingServiceImpl implements StaffTimeReportingService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    StaffTimeReportingDao dao;

    public void setDao(StaffTimeReportingDao dao) {
        this.dao = dao;
    }

    @Override
    @Cacheable(value="leaveSummary", key="#emplId + #dateInPayPeriod.toString()")
    public PayPeriodDailyLeaveTimeSummary getLeaveHoursReported(PortletRequest request, String emplId, LocalDate dateInPayPeriod) {
        log.debug("Invoking dao to get leave summary for employee ID {}, date {}", emplId, dateInPayPeriod);
        return dao.getLeaveHoursReported(emplId, dateInPayPeriod);
    }

    @Override
    @CacheEvict(value="leaveSummary", allEntries = true)
    public void updateLeaveTimeReported(PortletRequest request, String emplId, List<TimePeriodEntry> updatedTimesheet) {
        log.debug("Evicting all entries from leaveSummary cache due to update from employee ID {}", emplId);
        dao.updateLeaveTimeReported(emplId, updatedTimesheet);
    }

    @Override
    @Cacheable(value="leaveSummary", key="#emplId")
    public List<LeaveTimeBalance> getLeaveBalance(PortletRequest request, String emplId) {
        log.debug("Invoking dao to get leave balance for employee ID {}", emplId);
        return dao.getLeaveBalance(emplId);
    }
}
