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

package org.apereo.portlet.hr.web.timereporting.staffleave.balance;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import edu.wisc.web.security.portlet.primaryattr.PrimaryAttributeUtils;
import org.apache.commons.lang.StringUtils;
import org.apereo.portlet.hr.HrPortletRuntimeException;
import org.apereo.portlet.hr.model.timereporting.LeaveSummary;
import org.apereo.portlet.hr.timereporting.service.StaffTimeReportingService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * Displays a Sick and Vacation Balance information for the employee.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

@Controller
@RequestMapping("VIEW")
public class StaffLeaveBalancesController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private StaffTimeReportingService sickVacationService;

    @Autowired(required = true)
    public void setSickVacationService(StaffTimeReportingService sickVacationService) {
        this.sickVacationService = sickVacationService;
    }

    @RenderMapping
    public String render(final RenderRequest request, final RenderResponse response, Map model) {
        final String emplId = PrimaryAttributeUtils.getPrimaryId();
        if (StringUtils.isBlank(emplId)) {
            throw new HrPortletRuntimeException("Unable to obtain employee ID. Check configuration");
        }

        try {
            LeaveSummary leaveSummary = sickVacationService.getLeaveSummary(request, emplId);
            model.put("leaveSummary", leaveSummary);
        } catch (HrPortletRuntimeException e) {
            model.put("error", e.getMessage());
        }
        model.put("currentDate", new LocalDate());
        // Include preferences in case institution's custom JSP wants to pull anything else from portlet preferences.
        model.put("prefs", request.getPreferences().getMap());

        return "staffLeaveBalances";

    }

}
