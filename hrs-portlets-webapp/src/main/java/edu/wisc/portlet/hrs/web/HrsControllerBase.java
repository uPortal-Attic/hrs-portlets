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

package edu.wisc.portlet.hrs.web;

import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.wisc.hr.dao.url.HrsUrlDao;

/**
 * Common functions and dependencies for HRS controllers
 * 
 * @author Eric Dalquist
 * @version $Revision: 1.2 $
 */
public class HrsControllerBase {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private String notificationPreferences = "notification";
    private String helpUrlPreferences = "helpUrl";
    private HrsUrlDao hrsUrlDao;


    public void setNotificationPreferences(String notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    @Autowired
    public void setHrsUrlDao(HrsUrlDao hrsUrlDao) {
        this.hrsUrlDao = hrsUrlDao;
    }
    
    /**
     * Populate the ModelMap with the navigation links from the portlet preferences
     */
    @ModelAttribute("helpUrl")
    public final String getNavLinks(PortletRequest request) {
        final PortletPreferences preferences = request.getPreferences();
        return preferences.getValue(this.helpUrlPreferences, "#");
    }
    
    /**
     * Populate the ModelMap with the notification message
     */
    @ModelAttribute("notification")
    public final String getNotification(PortletRequest request) {
        final PortletPreferences preferences = request.getPreferences();
        
        return preferences.getValue(this.notificationPreferences, null);
    }
    
    /**
     * Populate the ModelMap with the HRS Urls
     */
    @ModelAttribute("hrsUrls")
    public final Map<String, String> getHrsUrls() {
        return this.hrsUrlDao.getHrsUrls();
    }
}