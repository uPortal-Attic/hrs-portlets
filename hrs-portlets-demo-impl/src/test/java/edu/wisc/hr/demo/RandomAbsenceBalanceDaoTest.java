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
import edu.wisc.hr.dm.absbal.AbsenceBalance;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Unit test for RandomAbsenceBalanceDao.
 *
 * Demonstrates that the DAO remembers and consistently returns the same data for a given emplId.
 *
 * Provides a utility method for printing some examples.
 */
public class RandomAbsenceBalanceDaoTest {

    private AbsenceBalanceDao dao = new RandomAbsenceBalanceDao();

    @Test
    public void returnsSameDataForAGivenEmplIdOnSubsequentCall() {

        List<AbsenceBalance> absenceBalances = dao.getAbsenceBalance("petro");

        assertSame(absenceBalances, dao.getAbsenceBalance("petro"));
        assertNotSame(absenceBalances, dao.getAbsenceBalance("bramhall"));

    }


    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
            "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println("Absence balances for [" + emplId + "]");

            List<AbsenceBalance> absenceBalances = dao.getAbsenceBalance(emplId);

            for (AbsenceBalance balance : absenceBalances ) {

                System.out.println(balance);
            }

            System.out.println();

        }

    }

}
