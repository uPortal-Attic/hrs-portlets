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

package edu.wisc.portlet.hrs.web.timeabs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.tlpayable.TimeSheetDao;
import edu.wisc.hr.dm.tlpayable.TimeSheet;
import edu.wisc.portlet.hrs.web.EmplIdUtils;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class TimeSheetDataController {
    private TimeSheetDao timeSheetDao;
    
    @Autowired
    public void setTimeSheetDao(TimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    
    @Secured({"ROLE_VIEW_WEB_CLOCK", "ROLE_VIEW_TIME_CLOCK", "ROLE_VIEW_TIME_SHEET"})
    @ResourceMapping("timeSheets")
    public String getTimeSheets(ModelMap modelMap) {
        final String emplid = EmplIdUtils.getEmplId();

        final List<TimeSheet> timeSheets = this.timeSheetDao.getTimeSheets(emplid);

        modelMap.addAttribute("report", timeSheets);
        
        return "reportAttrJsonView";
    }

}
