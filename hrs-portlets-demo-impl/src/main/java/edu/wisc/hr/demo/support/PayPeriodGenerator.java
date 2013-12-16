/*
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

package edu.wisc.hr.demo.support;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

/**
 * Generates pay periods.
 */
public class PayPeriodGenerator {

    public static final int PERIOD_START_DAY = DateTimeConstants.SUNDAY;

    public static final int PERIOD_DURATION_IN_DAYS = 14;


    /**
     * Returns a List of pay periods, where pay periods are Strings of the form
     * "Date - Date", as in "2013-08-02 - 2013-08-16".
     * @param howMany a positive integer or zero
     * @return a List of howMany Strings representing pay periods
     * @throws IllegalArgumentException if howMany < 0
     */
    public List<String> payPeriods(int howMany) {

        if (howMany < 0) {
            throw new IllegalArgumentException("Cannot generate negative number of pay periods.");
        }

        // if this method were expensive or frequently called we could cache results since it's the same
        // result for each call for a given howMany.  Indeed, could further optimize since howMany( N + 1) is
        // howMany( N) with an additional pay period prepended.
        //
        // compute cycles are cheap and premature optimization is

        List<String> payPeriods = new LinkedList<String>();

        int daysAgoToStart = ((PERIOD_DURATION_IN_DAYS + 2) * howMany);

        LocalDate startNextPayPeriod = new LocalDate().minusDays(daysAgoToStart);

        // TODO: calculate the exact number of days to add once and skip the loop
        while (startNextPayPeriod.getDayOfWeek() != PERIOD_START_DAY) {
            startNextPayPeriod = startNextPayPeriod.plusDays(1);
        }

        // startNextPayPeriod is now a PERIOD_START_DAY (e.g., Sunday) sufficiently long ago that
        // we can generate howMany periods from there and not quite hit today


        for (int i = 0; i < howMany; i++) {
            LocalDate startThisPayPeriod = startNextPayPeriod;
            LocalDate endThisPayPeriod = startThisPayPeriod.plusDays(PERIOD_DURATION_IN_DAYS - 1);

            // TODO: use a format string for better self-respect
            String payPeriodRepresentation =
                    startThisPayPeriod.toString().concat(" - ").concat(endThisPayPeriod.toString());

            payPeriods.add(payPeriodRepresentation);

            startNextPayPeriod = endThisPayPeriod.plusDays(1);
        }

        return payPeriods;
    }
}
