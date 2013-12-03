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
 * Various utility methods related to handling time
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class HhMmTimeUtility implements TimeParser {
    private static final String COLON = ":";

    public int computeMinutes(String timeWorked) {
        return parseHHMMStringToMinutes(timeWorked);
    }

    /**
     * Return the number of minutes for an hh:mm time value.
     * @param time Time value (must not be null or blank string)
     * @return Time value converted to minutes
     */
    public static int parseHHMMStringToMinutes(String time) {
        String[] values = time.split(COLON);
        if (values.length == 2) {
            try {
                int hours = Integer.parseInt(values[0]);
                int minutes = Integer.parseInt(values[1]);
                return hours * 60 + minutes;
            } catch (NumberFormatException e) {
                // fall through to error.
            }
        }
        throw new HrPortletRuntimeException("Invalid Time entry not HH:MM, was '" + time);
    }

    /**
     * Converts minutes to Hh:MM format.  Null is considered 0 minutes.  If hours are less than 10, a single hours
     * digit is returned.
     * @param minutes Number of minutes
     * @return minutes represented as Hh:MM format
     */
    public static String toHhMm(Integer minutes) {
        minutes = minutes == null ? 0 : minutes;
        return Integer.toString(minutes / 60) + COLON + getDoubleDigit(minutes % 60);
    }

    private static String getDoubleDigit(int number) {
        return (number < 10) ? "0"+number : new Integer(number).toString();
    }

}
