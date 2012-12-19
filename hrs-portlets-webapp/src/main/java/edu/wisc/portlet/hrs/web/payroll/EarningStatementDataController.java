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

package edu.wisc.portlet.hrs.web.payroll;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.PortletResourceProxyResponse;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.ernstmt.EarningStatementDao;
import edu.wisc.hr.dm.ernstmt.EarningStatement;
import edu.wisc.hr.dm.ernstmt.EarningStatements;
import edu.wisc.portlet.hrs.web.EmplIdUtils;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class EarningStatementDataController {
    private EarningStatementDao earningStatementDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }
    
    @Autowired
    public void setEarningStatementDao(EarningStatementDao earningStatementDao) {
        this.earningStatementDao = earningStatementDao;
    }

    @ResourceMapping("earningStatements")
    public String getEarningStatements(ModelMap modelMap) {
        final String emplid = EmplIdUtils.getEmplId();
        final EarningStatements earningStatements = this.earningStatementDao.getEarningStatements(emplid);
        
        final List<EarningStatement> statements = earningStatements.getEarningStatements();
        modelMap.addAttribute("report", statements);
        
        return "reportAttrJsonView";
    }
    //Server
    //
    @ResourceMapping("earning_statement.pdf")
    public void getEarningsStatement(
            @RequestParam("docId") String docId, 
            ResourceResponse response) {
        
        final String emplid = EmplIdUtils.getEmplId();
        this.earningStatementDao.getEarningStatement(emplid, docId, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
}
