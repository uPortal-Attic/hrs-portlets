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

package org.apereo.portlet.hr.dao.timereporting;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apereo.portlet.hr.model.timereporting.TimePunchEntry;
import org.apereo.portlet.hr.HrPortletRuntimeException;

/**
 * Dao to support employees punching in a time clock for jobs being worked.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public interface StaffTimePunchDao {

    /**
     * Returns a list of <code>TimePunchEntry</code> items for the employee.  The order of the list
     * determines the order the items are displayed on the UI.
     *
     * @param request Portlet Request
     * @param emplId Employee ID
     * @return List of <code>TimePunchEntry</code> items
     */
    List<TimePunchEntry> getTimePunchEntries(PortletRequest request, String emplId);

    /**
     * Starts logging time for the employee to the indicated job code.  The implementation is responsible for
     * determining what to do when the employee punches into the same job code without punching out, or if the employee
     * punches into a job code when already punched into other job codes.  A typical response would be to fail
     * processing the punch-in and throw an HrPortletRuntimeException to notify the user of the error.
     *
     * @param request Portlet Request
     * @param emplId Employee ID
     * @param jobCode Job Code
     * @param clientIP IP address of the client
     * @throws HrPortletRuntimeException Message content to display to the user (typically some form of failure)
     */
    void punchInTimeClock(PortletRequest request, String emplId, int jobCode, String clientIP);

    /**
     * Stops logging time for the employee to the indicated job code. The implementation is responsible for
     * determining what to do when the employee is not punched in to the job code.  A typical response would be to
     * fail processing the punch out and throw an HrPortletRuntimeException to notify the user of the error.
     *
     * @param request Portlet Request
     * @param emplId
     * @param jobCode
     * @param clientIP
     * @throws HrPortletRuntimeException Message content to display to the user (typically some form of failure)
     */
    void punchOutTimeClock(PortletRequest request, String emplId, int jobCode, String clientIP);
}
