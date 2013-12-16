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

import edu.wisc.hr.dao.mssabs.ManagerAbsenceDao;
import edu.wisc.hr.dm.mssabs.ManagedAbsence;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for RandomManagerAbsenceDao.
 * Demonstates that the DAO returns the same data for a given employee ID on subsequent calls.
 * Offers a utility method for printing exmaples.
 */
public class RandomManagerAbsenceDaoTest {

    private ManagerAbsenceDao dao = new RandomManagerAbsenceDao();

    @Test
    public void returnsSameManagedAbsencesOnSubsequentCallForSameEmplId() {

        List<ManagedAbsence> absences = dao.getManagedAbsences("petro");

        assertSame(absences, dao.getManagedAbsences("petro"));
        assertNotSame(absences, dao.getManagedAbsences("gilbert"));

    }


    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId: emplIds) {

            System.out.println(emplId + "'s managed absences:");

            List<ManagedAbsence> managedAbsences = dao.getManagedAbsences(emplId);

            for (ManagedAbsence absence : managedAbsences) {
                System.out.println("  " + absence.getName() + " : " + absence.getStatus());
            }

            System.out.println();

        }

    }

}
