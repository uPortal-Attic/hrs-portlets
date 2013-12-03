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

package edu.byu.hr.timereporting.util;

import edu.byu.hr.HrPortletRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * Utility methods for time calculation
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class TimeCalculations {

    /**
     * Utility method to accept a Period and returns a new period that added the time-worked amount in the form
     * of hh:mm and returns a new Period.
     *
     * @param total
     * @param timeWorked
     * @return
     */
    public static Period addTime(Period total, String timeWorked) {
        Period updatedTotal = total;
        if (StringUtils.isNotBlank(timeWorked)) {
            boolean error = false;
            String[] values = timeWorked.split(":");
            if (values.length == 2) {
                try {
                    int hours = Integer.parseInt(values[0]);
                    int minutes = Integer.parseInt(values[1]);
                    updatedTotal = total.plusHours(hours).plusMinutes(minutes);
                } catch (NumberFormatException e) {
                    error = true;
                }
            } else {
                error = true;
            }
            if (error) {
                throw new HrPortletRuntimeException("Invalid Time entry not HH:MM, was '" + timeWorked);
            }
        }
        return updatedTotal;
    }

    private static int parseHHMMStringToMinutes(String time) {
        String[] values = time.split(":");
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

    public static String convertPeriodToHHMM(Period period) {
        Period timeStandardized = period.normalizedStandard(PeriodType.time());
        return getDoubleDigit(timeStandardized.getHours()) + ":" + getDoubleDigit(timeStandardized.getMinutes());
    }

    private static String getDoubleDigit(int number) {
        return (number < 10) ? "0"+number : new Integer(number).toString();
    }

    public static int computeMinutes(String timeWorked) {
        return parseHHMMStringToMinutes(timeWorked);
    }

}
