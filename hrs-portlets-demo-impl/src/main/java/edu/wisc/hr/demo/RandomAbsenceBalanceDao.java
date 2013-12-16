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

import edu.wisc.hr.dao.absbal.AbsenceBalanceDao;
import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dm.absbal.AbsenceBalance;

import edu.wisc.hr.dm.person.PersonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Demo absence balance DAO that, when first asked about absence balances for a given emplId, randomly generates some.
 * It remembers these in-memory (which is what makes this a DAO rather than just a random generator.)
 */
@Repository
public class RandomAbsenceBalanceDao
    implements AbsenceBalanceDao {

    private Random random = new Random();

    private ContactInfoDao contactInfoDao = new RandomContactInfoDao();

    /**
     * Map from employee ID to a List of absence balances in no particular order.
     * Once generated, absence balance List for each user is remembered here so the same List will be
     * returned on subsequent requests.
     */
    private Map emplIdToAbsenseBalanceList = new HashMap<String, List<AbsenceBalance>>();

    /**
     * A bunch of made-up entitlements for use as entitlement labels.
     * Any given user will have a random subset of these.
     */
    private static String[] ENTITLEMENTS = {
            "Annual Leave Reserve Account",
            "Classified Furlough Allocated",
            "Comp Time Balance",
            "Legal Holiday  YTD",
            "Personal Holiday",
            "Sabbatical",
            "Classified Sick Leave",
            "Unclassified Sick Leave",
            "Vacation Allocation",
            "Pet Bereavement Leave",
            "Vacation Carryover",
            "Professional Development Leave",
            "Personal Time",
            "Me Time",
            "Volunteer Leave",
            "20 Percent Time",
            "Childcare Leave",
            "Adult Care Leave",
            "Pet Care Leave",
            "Triathlon Leave",
            "Parent-Teacher Conferences Leave",
            "Theatrical Appreciation Time",
            "Continuing Education Time"};

    /**
     * Ceiling on entitlement balance.
     */
    private static int MAX_ENTITLEMENT_BALANCE = 2001;


    @Override
    public List<AbsenceBalance> getAbsenceBalance(String emplId) {

        if (emplIdToAbsenseBalanceList.containsKey(emplId)) {
            return (List<AbsenceBalance>) emplIdToAbsenseBalanceList.get(emplId);
        }

        int howManyBalances = random.nextInt(20);

        List absenceBalances = new LinkedList<AbsenceBalance>();

        for (int i = 0; i < howManyBalances; i++) {
            AbsenceBalance absenceBalance = new AbsenceBalance();

            int balanceWhole = random.nextInt(MAX_ENTITLEMENT_BALANCE - 1);
            int balanceQuarters = random.nextInt(4);

            double balanceDouble = (balanceQuarters / 4.0) + balanceWhole;

            BigDecimal balance = new BigDecimal(balanceDouble);

            absenceBalance.setBalance(balance);
            absenceBalance.setEntitlement(randomEntitlementName());

            PersonInformation contactInfo = contactInfoDao.getPersonalData(emplId);

            absenceBalance.setJob( contactInfo.getPrimaryJob() );

            absenceBalances.add(absenceBalance);
        }

        this.emplIdToAbsenseBalanceList.put(emplId, absenceBalances);

        return absenceBalances;
    }

    private String randomEntitlementName() {

        return this.ENTITLEMENTS[ random.nextInt(this.ENTITLEMENTS.length) ];

    }

    public ContactInfoDao getContactInfoDao() {
        return contactInfoDao;
    }

    @Autowired
    public void setContactInfoDao(ContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }
}
