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
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Random implementation of ManagerTimeDao API.
 */
@Repository
public class RandomManagerTimeDao
    implements ManagerTimeDao {

    private Map<String, List<ManagedTime>> emplIdToManagedTimes = new HashMap<String, List<ManagedTime>>();

    private Random random = new Random();

    @Override
    public List<ManagedTime> getManagedTimes(String emplId) {

        if (this.emplIdToManagedTimes.containsKey(emplId)) {
            return this.emplIdToManagedTimes.get(emplId);
        }

        List<ManagedTime> managedTimes = new LinkedList<ManagedTime>();

        int howManyManagedTimes = random.nextInt(20);

        for (int i = 0; i < howManyManagedTimes; i++) {

            ManagedTime managedTime = new ManagedTime();

            // TODO: Make name and status more realistic
            managedTime.setName("What's a managed time name?");
            managedTime.setStatus("Status?");

            managedTimes.add(managedTime);

        }

        this.emplIdToManagedTimes.put(emplId, managedTimes);


        return managedTimes;
    }

    @Override
    public void refreshManagedTimes(String emplId) {
        // does nothing in this implementation
    }
}
