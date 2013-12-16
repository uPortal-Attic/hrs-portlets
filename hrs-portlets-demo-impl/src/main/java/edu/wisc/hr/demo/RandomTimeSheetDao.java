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

import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dao.tlpayable.TimeSheetDao;
import edu.wisc.hr.dm.person.Job;
import edu.wisc.hr.dm.tlpayable.TimeSheet;
import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Random implementation of the TimeSheetDao API.
 * Makes no attempt to be threadsafe.
 */
@Repository
public class RandomTimeSheetDao
    implements TimeSheetDao {

    private Map<String, List<TimeSheet>> emplIdToListOfTimeSheets = new HashMap<String, List<TimeSheet>>();

    private Random random = new Random();

    public ContactInfoDao getContactInfoDao() {
        return contactInfoDao;
    }

    @Autowired
    public void setContactInfoDao(ContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }

    private ContactInfoDao contactInfoDao = new RandomContactInfoDao();


    @Override
    public List<TimeSheet> getTimeSheets(String emplId) {

        if (emplIdToListOfTimeSheets.containsKey(emplId)) {
            return emplIdToListOfTimeSheets.get(emplId);
        }

        List<TimeSheet> timesheets = new LinkedList<TimeSheet>();

        Job primaryJob = this.contactInfoDao.getPersonalData(emplId).getPrimaryJob();

        int howManyTimeSheets = random.nextInt(20);

        for (int i = 0; i < howManyTimeSheets; i++) {
            TimeSheet timeSheet = new TimeSheet();

            int randomNumberOfDaysAgo = random.nextInt(1000);
            DateMidnight midnightToday = new DateMidnight();
            DateMidnight midnightAWhileAgo = midnightToday.minusDays(randomNumberOfDaysAgo);

            timeSheet.setDate(midnightAWhileAgo);
            timeSheet.setJob( primaryJob );
            timeSheet.setStatus("Status?");

            int hours = 20 + random.nextInt(30);
            int quarterHours = random.nextInt(4);

            double total = hours + (0.25 * quarterHours);

            timeSheet.setTotal(new BigDecimal(total));
            timeSheet.setType("Type?");

            timesheets.add(timeSheet);

        }

        this.emplIdToListOfTimeSheets.put(emplId, timesheets);

        return timesheets;
    }
}
