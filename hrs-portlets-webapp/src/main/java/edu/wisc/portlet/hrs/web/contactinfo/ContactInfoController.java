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

package edu.wisc.portlet.hrs.web.contactinfo;

import java.util.Arrays;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.handler.PortletRequestMethodNotSupportedException;

import edu.wisc.hr.dao.bnsemail.BusinessEmailUpdateDao;
import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dm.bnsemail.PreferredEmail;
import edu.wisc.hr.dm.person.PersonInformation;
import edu.wisc.portlet.hrs.web.EmplIdUtils;
import edu.wisc.portlet.hrs.web.HrsControllerBase;

/**
 * @author Eric Dalquist
 * @version $Revision: 1.3 $
 */
@Controller
@RequestMapping("VIEW")
public class ContactInfoController extends HrsControllerBase {
    private final EmailValidator emailValidator = EmailValidator.getInstance();
    
    private String businessEmailRolesPreferences = "businessEmailRoles";
    private ContactInfoDao contactInfoDao;
    private BusinessEmailUpdateDao businessEmailUpdateDao;
    
    public void setBusinessEmailRolesPreferences(String businessEmailRolesPreferences) {
        this.businessEmailRolesPreferences = businessEmailRolesPreferences;
    }

    @Autowired
    public void setBusinessEmailUpdateDao(BusinessEmailUpdateDao businessEmailUpdateDao) {
        this.businessEmailUpdateDao = businessEmailUpdateDao;
    }

    @Autowired
    public void setContactInfoDao(ContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }

    @RenderMapping
    public String viewContactInfo(ModelMap model, PortletRequest request) {
        final String emplId = EmplIdUtils.getEmplId();
        
        final PersonInformation contactInformation = this.contactInfoDao.getPersonalData(emplId);
        model.addAttribute("contactInformation", contactInformation);
        
        final boolean showBusinessEmail = showBusinessEmail(request);
        model.addAttribute("showBusinessEmail", showBusinessEmail);
        if (showBusinessEmail) {
            final PreferredEmail preferredEmail = this.businessEmailUpdateDao.getPreferedEmail(emplId);
            model.addAttribute("preferredEmail", preferredEmail);
        }
        
        return "contactInfo";
    }

    //TODO switch to spring-sec role check?
    protected boolean showBusinessEmail(PortletRequest request) {
        final PortletPreferences preferences = request.getPreferences();
        final String[] roles = preferences.getValues(this.businessEmailRolesPreferences, new String[0]);
        
        for (final String businessEmailRole : roles) {
            if (request.isUserInRole(businessEmailRole)) {
                this.logger.debug("Showing business email info, user is in role: {}", businessEmailRole);
                return true;
            }
        }
        
        this.logger.debug("Hiding business email info, user is not in any of the roles: {}", Arrays.asList(roles));
        
        return false;
    }
    
    @ResourceMapping("businessEmailAddress")
    public String updateBusinessEmailAddress(
            @RequestParam("email") String email,
            ResourceRequest request,
            ModelMap modelMap) throws PortletRequestMethodNotSupportedException {
        
        //TODO can I move this into the annotations?
        //TODO add method support to portlet annotations in portlet contrib
        if (!"POST".equals(request.getMethod())) {
            throw new PortletRequestMethodNotSupportedException(request.getMethod(), new String[] { "POST" });
        }
        
        final String emplid = EmplIdUtils.getEmplId();
        
        email = email.trim();
        
        if (!StringUtils.isEmpty(email)) {
            if (emailValidator.isValid(email)) {
                this.businessEmailUpdateDao.updatePreferedEmail(emplid, email);
            }
            else {
                logger.warn("User {} submitted invalid email address {} the change request will be ignored.", emplid, email);
            }
        }
        
        return this.getBusinessEmailAddress(modelMap);
    }
    
    protected String getBusinessEmailAddress( ModelMap modelMap) {
        
        final String emplid = EmplIdUtils.getEmplId();
        
        final PreferredEmail preferredEmail = this.businessEmailUpdateDao.getPreferedEmail(emplid);
        final String updatedEmail = preferredEmail.getEmail();
        modelMap.addAttribute("email", updatedEmail);
        
        return "jsonView";
    }
}
