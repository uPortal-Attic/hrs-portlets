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

package edu.wisc.portlet.hrs.web.benefits;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.jasig.springframework.web.client.PortletResourceProxyResponse;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.benstmt.BenefitStatementDao;
import edu.wisc.hr.dm.benstmt.BenefitStatement;
import edu.wisc.hr.dm.benstmt.BenefitStatements;
import edu.wisc.portlet.hrs.util.HrsDownloadControllerUtils;

import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;

import com.google.common.collect.ComparisonChain;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class BenefitStatementDataController {
    private BenefitStatementDao benefitStatementDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }

    @Autowired
    public void setBenefitStatementDao(BenefitStatementDao benefitStatementDao) {
        this.benefitStatementDao = benefitStatementDao;
    }
    
    @ResourceMapping("benefitStatements")
    public String getBenefitStatements(ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        final BenefitStatements benefitStatements = this.benefitStatementDao.getBenefitStatements(emplid);

        final List<BenefitStatement> statements = benefitStatements.getBenefitStatements();
        ComparatorChain chainSort = new ComparatorChain();
        chainSort.addComparator(new BenefitStatementNameComparator());
        chainSort.addComparator(new BenefitStatementYearComparator());
        
        Collections.sort(statements,chainSort);
        modelMap.addAttribute("report", statements);
        
        return "reportAttrJsonView";
    }

    @ResourceMapping("benefits.pdf")
    public void getBenefitStatement(
            @RequestParam("mode") String mode,
            @RequestParam("docId") String docId, 
            @RequestParam("year") int year,
            ResourceResponse response) {

        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        HrsDownloadControllerUtils.setResponseHeaderForDownload(response, "benefits", "PDF");
        this.benefitStatementDao.getBenefitStatement(emplid, year, docId, mode, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
    
    private class BenefitStatementYearComparator implements Comparator<BenefitStatement> {

		@Override
		public int compare(BenefitStatement o1, BenefitStatement o2) {
			return o2.getYear().compareTo(o1.getYear());
		}
    	
    }
    
    private class BenefitStatementNameComparator implements Comparator<BenefitStatement> {

		@Override
		public int compare(BenefitStatement o1, BenefitStatement o2) {
			String o1Type = o1.getName().substring(0,o1.getName().indexOf(" "));
			String o2Type = o2.getName().substring(0,o2.getName().indexOf(" "));
			return o1Type.compareTo(o2Type);
		}
    	
    }
}
