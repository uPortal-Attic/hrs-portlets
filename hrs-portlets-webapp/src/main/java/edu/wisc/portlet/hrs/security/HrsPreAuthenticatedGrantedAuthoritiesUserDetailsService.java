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
package edu.wisc.portlet.hrs.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;

import edu.wisc.hr.dao.roles.HrsRolesDao;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributePortletAuthenticationDetails;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributePortletPreAuthenticatedAuthenticationDetailsSource;

/**
 * Uses the primary attribute retrieved by {@link PrimaryAttributePortletPreAuthenticatedAuthenticationDetailsSource}
 * to look up the HRS roles for the user
 * 
 * @author Eric Dalquist
 */
public class HrsPreAuthenticatedGrantedAuthoritiesUserDetailsService extends
        PreAuthenticatedGrantedAuthoritiesUserDetailsService {
    
    private HrsRolesDao hrsRolesDao;

    @Autowired
    public void setHrsRolesDao(HrsRolesDao hrsRolesDao) {
        this.hrsRolesDao = hrsRolesDao;
    }

    @Override
    protected UserDetails createuserDetails(Authentication token, Collection<? extends GrantedAuthority> authorities) {
        final PrimaryAttributePortletAuthenticationDetails details = (PrimaryAttributePortletAuthenticationDetails)token.getDetails();

        final String emplId = details.getPrimaryAttribute();
        final Set<String> hrsRoles = this.hrsRolesDao.getHrsRoles(emplId);
        
        final Collection<GrantedAuthority> additionalGrantedAuthorities = new ArrayList<GrantedAuthority>(hrsRoles.size() + authorities.size());
        additionalGrantedAuthorities.addAll(authorities);
        
        for (final String hrsRole : hrsRoles) {
            additionalGrantedAuthorities.add(new SimpleGrantedAuthority(hrsRole));
        }
        
        return super.createuserDetails(token, additionalGrantedAuthorities);
    }

}
