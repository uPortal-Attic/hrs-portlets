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

import edu.wisc.hr.dao.abshis.AbsenceHistoryDao;
import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dm.abshis.AbsenceHistory;
import edu.wisc.hr.dm.person.Job;
import edu.wisc.hr.dm.person.PersonInformation;
import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 */
@Repository
public class RandomAbsenceHistoryDao
    implements AbsenceHistoryDao {

    private Random random = new Random();


    public ContactInfoDao getContactInfoDao() {
        return contactInfoDao;
    }

    /**
     * Uses the contact info DAO to get the user's primary job to use as the job on the absence histories.
     * @return
     */
    @Autowired
    public void setContactInfoDao(ContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }

    private ContactInfoDao contactInfoDao = new RandomContactInfoDao();

    private Map emplIdToAbsenceHistory = new HashMap<String, List<AbsenceHistory>>();

    @Override
    public List<AbsenceHistory> getAbsenceHistory(String emplId) {

        if (emplIdToAbsenceHistory.containsKey(emplId)) {
            return (List<AbsenceHistory>) emplIdToAbsenceHistory.get(emplId);
        }

        int howManyAbsences = random.nextInt(20);

        List absenceHistories = new LinkedList<AbsenceHistory>();


        for (int i = 0; i < howManyAbsences; i++) {
            AbsenceHistory absenceHistory = new AbsenceHistory();

            random.nextLong();

            // generate start date
            int year = 2013 - random.nextInt(10); // 2004-2013
            int monthOfYear = random.nextInt(12) + 1; // 1-12
            int dayOfMonth = random.nextInt(28) + 1; // 1-28

            DateMidnight startDate = new DateMidnight(year, monthOfYear, dayOfMonth);

            int daysDuration = random.nextInt(100);
            DateMidnight endDate = startDate.plusDays( daysDuration );


            PersonInformation personInfo = this.contactInfoDao.getPersonalData(emplId);
            Job primaryJob = personInfo.getPrimaryJob();

            absenceHistory.setStart(startDate);
            absenceHistory.setEnd(endDate);
            absenceHistory.setTotal(new BigDecimal(daysDuration));
            absenceHistory.setName("WhatsAnAbsenceHistoryName?");
            absenceHistory.setJob(primaryJob);
            absenceHistory.setStatus("WhatsAnAbsenceHistoryStatus?");

            absenceHistories.add(absenceHistory);
        }

        this.emplIdToAbsenceHistory.put(emplId, absenceHistories);

        return absenceHistories;


    }
}
