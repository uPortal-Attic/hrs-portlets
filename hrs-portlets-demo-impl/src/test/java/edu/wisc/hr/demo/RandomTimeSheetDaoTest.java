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

package edu.wisc.hr.demo;

import edu.wisc.hr.dao.tlpayable.TimeSheetDao;
import edu.wisc.hr.dm.tlpayable.TimeSheet;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for RandomTimeSheetDao.
 *
 * Demonstrates that the dao remembers and returns the same data for a given user on subsequent calls.
 *
 * Offers a utility method for printing some example random timesheets.
 */
public class RandomTimeSheetDaoTest {

    private TimeSheetDao dao = new RandomTimeSheetDao();

    @Test
    public void returnsSameTimeSheetForGivenEmplIdOnSubsequentCall() {

        List<TimeSheet> petroTimesheets = dao.getTimeSheets("petro");

        assertSame(petroTimesheets, dao.getTimeSheets("petro"));
        assertNotSame(petroTimesheets, dao.getTimeSheets("walker"));

    }

    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println(emplId + "'s Time Sheets");

            List<TimeSheet> timeSheets = dao.getTimeSheets(emplId);

            for (TimeSheet sheet : timeSheets) {

                System.out.println("   " + sheet.getDate() + " : " + sheet.getStatus() + " : " + sheet.getType()
                        + " : " + sheet.getJob().getTitle() + " [" + sheet.getJob().getDepartmentName() + "] : "
                        + sheet.getTotal());
            }


            System.out.println();
        }

    }

}
