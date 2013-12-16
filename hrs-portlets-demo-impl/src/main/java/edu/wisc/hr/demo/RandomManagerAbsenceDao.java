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
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Random implementation of ManagerAbsenceDao.
 */
@Repository
public class RandomManagerAbsenceDao
    implements ManagerAbsenceDao {


    private Map<String, List<ManagedAbsence>> emplIdToManagedAbsenceList = new HashMap<String, List<ManagedAbsence>>();

    private Random random = new Random();

    @Override
    public List<ManagedAbsence> getManagedAbsences(String emplId) {


        if (emplIdToManagedAbsenceList.containsKey(emplId)) {
            return emplIdToManagedAbsenceList.get(emplId);
        }


        int howManyManagedAbsences = random.nextInt(20);

        List<ManagedAbsence> managedAbsences = new LinkedList<ManagedAbsence>();

        for (int i = 0; i < howManyManagedAbsences; i++) {

            ManagedAbsence absence = new ManagedAbsence();

            absence.setName("What is an absence name?");
            absence.setStatus("Status?");

            managedAbsences.add(absence);

        }


        this.emplIdToManagedAbsenceList.put(emplId, managedAbsences);

        return managedAbsences;
    }

    @Override
    public void refreshManagedAbsences(String emplId) {
        // does nothing in this implementation
    }
}
