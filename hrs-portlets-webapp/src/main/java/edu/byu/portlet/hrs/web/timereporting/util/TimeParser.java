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

package edu.byu.portlet.hrs.web.timereporting.util;

import edu.byu.hr.HrPortletRuntimeException;

/**
 * Interface related to parsing time values from the UI to minutes
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public interface TimeParser {

    /**
     * Convert a string time value from the UI to the number of minutes.
     * @param timeWorked Time worked in format determined by implementing class.  Null or empty value is
     *                   treated as 0 minutes.
     * @return Number of minutes
     * @throws HrPortletRuntimeException if the string is invalid.
     */
    public int computeMinutes(String timeWorked) throws HrPortletRuntimeException;
}
