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

package edu.wisc.portlet.hrs.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.mssabs.ManagerAbsenceDao;
import edu.wisc.hr.dm.mssabs.ManagedAbsence;
import edu.wisc.web.security.portlet.primaryattr.PrimaryAttributeUtils;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class ManagerAbsenceDataController {
    private ManagerAbsenceDao managerAbsenceDao;

    @Autowired
    public void setManagerAbsenceDao(ManagerAbsenceDao managerAbsenceDao) {
        this.managerAbsenceDao = managerAbsenceDao;
    }
    
    @Secured("ROLE_VIEW_MANAGED_ABSENCES")
    @ResourceMapping("managedAbsences")
    public String getManagedAbsences(@RequestParam(value="refresh", required=false) Boolean refresh, ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();

        if (refresh != null && refresh) {
            this.managerAbsenceDao.refreshManagedAbsences(emplid);
        }
        
        final List<ManagedAbsence> managedAbsences = this.managerAbsenceDao.getManagedAbsences(emplid);

        modelMap.addAttribute("report", managedAbsences);
        
        return "reportAttrJsonView";
    }
}
