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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasig.springframework.security.portlet.authentication.PortletPreAuthenticatedAuthenticationDetailsSource;
import org.jasig.springframework.security.portlet.authentication.PreAuthenticatedGrantedAuthoritiesPortletAuthenticationDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.portlet.ModelAndViewDefiningException;

public class PrimaryAttributePortletPreAuthenticatedAuthenticationDetailsSource extends
        PortletPreAuthenticatedAuthenticationDetailsSource {
    
    private String primaryUserAttributesPreference;

    public void setPrimaryUserAttributesPreference(String primaryUserAttributesPreference) {
        this.primaryUserAttributesPreference = primaryUserAttributesPreference;
    }
    
    @Override
    public PreAuthenticatedGrantedAuthoritiesPortletAuthenticationDetails buildDetails(PortletRequest context) {

        Collection<? extends GrantedAuthority> userGas = buildGrantedAuthorities(context);
        
        final String primaryUserAttribute = this.getPrimaryUserAttribute(context);

        PrimaryAttributePortletAuthenticationDetails result =
                new PrimaryAttributePortletAuthenticationDetails(context, userGas, primaryUserAttribute);
        
        return result;
    }

    /**
     * Get the user's primary attribute.
     *
     * @param primaryUserAttributesPreference The portlet preference that contains a list of user attributes to inspect in order. The first attribute with a value is returned.
     * @return The primary attribute, will never return null or empty string
     * @throws ModelAndViewDefiningException If no emplid is found
     */
    @SuppressWarnings("unchecked")
    protected final String getPrimaryUserAttribute(PortletRequest request) {
        final PortletPreferences preferences = request.getPreferences();
        final Map<String, String> userAttributes = (Map<String, String>)request.getAttribute(PortletRequest.USER_INFO);
        
        final String[] attributeNames = preferences.getValues(primaryUserAttributesPreference, new String[0]);
        for (final String attributeName : attributeNames) {
            final String emplid = userAttributes.get(attributeName);
            if (StringUtils.isNotEmpty(emplid)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Found emplid '" + emplid + "' for under attribute: " + attributeName);
                }
                
                return emplid;
            }
        }
        
        logger.warn("Could not find a value for any of the user attributes " + Arrays.toString(attributeNames) + " specified by preference: " + primaryUserAttributesPreference);
        
        throw new AccessDeniedException("No primary attribute found in attributes: " + Arrays.toString(attributeNames));
    }
}
