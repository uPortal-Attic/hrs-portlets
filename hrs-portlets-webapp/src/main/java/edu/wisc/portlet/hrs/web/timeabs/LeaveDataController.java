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

import java.util.Collection;
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

import edu.wisc.hr.dao.levstmt.LeaveStatementDao;
import edu.wisc.hr.dao.levstmt.StatementType;
import edu.wisc.hr.dm.levstmt.SummarizedLeaveStatement;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class LeaveDataController {
    private LeaveStatementDao leaveStatementDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }
    
    @Autowired
    public void setLeaveStatementDao(LeaveStatementDao leaveStatementDao) {
        this.leaveStatementDao = leaveStatementDao;
    }
    
    @ResourceMapping("leaveStatements")
    public String getLeaveStatements(ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();

        final Collection<SummarizedLeaveStatement> leaveStatements = this.leaveStatementDao.getLeaveStatements(emplid);
        
        modelMap.addAttribute("report", leaveStatements);
        
        return "reportAttrJsonView";
    }

    @ResourceMapping("leave_statement.pdf")
    public void getLeaveStatement(
            @RequestParam("docId") String docId,
            ResourceResponse response) {
        
        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        response.setProperty("Content-Disposition", "inline; filename=leave_statement.pdf");
        this.leaveStatementDao.getLeaveStatement(emplid, docId, StatementType.LEAVE, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
    
    @ResourceMapping("leave_furlough_report.pdf")
    public void getLeaveFurloughReport(
            @RequestParam("docId") String docId,
            ResourceResponse response) {
        
        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        response.setProperty("Content-Disposition", "inline; filename=leave_furlough_report.pdf");
        this.leaveStatementDao.getLeaveStatement(emplid, docId, StatementType.REPORT, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
    
    @ResourceMapping("missing_leave_report.pdf")
    public void getMissingLeaveReport(
            @RequestParam("docId") String docId,
            ResourceResponse response) {
        
        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        response.setProperty("Content-Disposition", "inline; filename=missing_leave_report.pdf");
        this.leaveStatementDao.getLeaveStatement(emplid, docId, StatementType.MISSING, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
}
