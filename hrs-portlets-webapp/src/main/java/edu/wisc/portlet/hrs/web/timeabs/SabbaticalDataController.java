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

import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jasig.springframework.web.client.PortletResourceProxyResponse;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.sabstmt.SabbaticalStatementDao;
import edu.wisc.hr.dm.sabstmt.SabbaticalReports;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class SabbaticalDataController {
    private SabbaticalStatementDao sabbaticalStatementDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }
    
    @Autowired
    public void setSabbaticalStatementDao(SabbaticalStatementDao sabbaticalStatementDao) {
        this.sabbaticalStatementDao = sabbaticalStatementDao;
    }

    
    @ResourceMapping("sabbaticalReports")
    public String getSabbaticalReports(ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();

        final SabbaticalReports sabbaticalReports = this.sabbaticalStatementDao.getSabbaticalReports(emplid);
        
        modelMap.addAttribute("report", sabbaticalReports.getSabbaticalReports());
        
        return "reportAttrJsonView";
    }

    @ResourceMapping("sabbatical_report.pdf")
    public void getSabbaticalReport(@RequestParam("docId") String docId, ResourceResponse response) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        response.setProperty("Content-Disposition", "inline; filename=sabbatical_report.pdf");
        this.sabbaticalStatementDao.getSabbaticalReport(emplid, docId, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
}
