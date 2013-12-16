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

import edu.wisc.hr.dm.abshis.AbsenceHistory;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for RandomAbsenceHistoryDao.
 *
 * Demonstrates that it remembers and returns the same absence history for a given emplId on subsequent call.
 * Provides utility method for printing examples of random absence histories.
 */
public class RandomAbsenceHistoryDaoTest {

    RandomAbsenceHistoryDao dao = new RandomAbsenceHistoryDao();

    @Test
    public void returnsSameAbsenceHistoryForEmplIdOnSubsequentCall() {

        List<AbsenceHistory> history = dao.getAbsenceHistory("petro");

        assertSame(history, dao.getAbsenceHistory("petro"));
        assertNotSame(history, dao.getAbsenceHistory("newman"));

    }

    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println("Absence balances for [" + emplId + "]");

            List<AbsenceHistory> histories = dao.getAbsenceHistory(emplId);

            for (AbsenceHistory history : histories) {
                System.out.println(history);
            }

            System.out.println();

        }

    }

}
