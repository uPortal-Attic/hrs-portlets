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

import edu.wisc.hr.dao.msstime.ManagerTimeDao;
import edu.wisc.hr.dm.msstime.ManagedTime;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for the RandomManagerTimeDao.
 * Tests that the dao consistently returns the same results for a given employee ID and
 * offers a utility method for printing examples.
 */
public class RandomManagerTimeDaoTest {

    private ManagerTimeDao dao = new RandomManagerTimeDao();

    @Test
    public void returnsSameInfoOnSubsequentCallForGivenEmplId() {

        List<ManagedTime> petroTime = dao.getManagedTimes("petro");

        assertSame(petroTime, dao.getManagedTimes("petro"));
        assertNotSame(petroTime, dao.getManagedTimes("lonas"));

    }

    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println(emplId + "'s Managed Times:");

            List<ManagedTime> managedTimes = dao.getManagedTimes(emplId);

            for (ManagedTime time : managedTimes) {
                System.out.println("   " + time.getName() + " : " + time.getStatus());
            }

            System.out.println();
        }

    }
}
