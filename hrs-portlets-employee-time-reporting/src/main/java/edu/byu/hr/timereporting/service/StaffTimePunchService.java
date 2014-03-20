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

package edu.byu.hr.timereporting.service;

import java.util.List;

import javax.portlet.PortletRequest;

import edu.byu.hr.model.timereporting.TimePunchEntry;

/**
 * Service interface definition for the Staff Time Punch portlet.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public interface StaffTimePunchService {

    /**
     * Returns a list of <code>TimePunchEntry</code> items for the employee.  The list order is the order the
     * items are displayed on the UI.
     * @param emplId Employee ID
     * @return List of <code>TimePUnchEntry</code> items
     */
    List<TimePunchEntry> getTimePunchEntries(PortletRequest request, String emplId, boolean refresh);

    /**
     * Starts logging time for the employee to the indicated job code.
     * @param emplId
     * @param jobCode
     */
    void punchInTimeClock (PortletRequest request, String emplId, int jobCode, String clientIP);

    /**
     * Stops logging time for the employee to the indicated job code.
     * @param emplId
     * @param jobCode
     * @param clientIP
     */
    void punchOutTimeClock (PortletRequest request, String emplId, int jobCode, String clientIP);
}
