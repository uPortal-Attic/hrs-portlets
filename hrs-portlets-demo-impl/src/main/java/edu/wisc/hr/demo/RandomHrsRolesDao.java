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


import edu.wisc.hr.dao.roles.HrsRolesDao;
import org.springframework.stereotype.Repository;

import java.util.*;

import static edu.wisc.hr.dao.roles.HrsRole.*;

/**
 * Demo HrsRolesDao implementation that randomly assigns user roles.
 */
@Repository
public class RandomHrsRolesDao
    implements HrsRolesDao {

    private Random random = new Random();

    private String[] possible_roles = {
            ROLE_VIEW_ABSENCE_HISTORIES.name(), ROLE_VIEW_MANAGED_ABSENCES.name(),
            ROLE_VIEW_MANAGED_TIMES.name(), ROLE_VIEW_TIME_CLOCK.name(), ROLE_VIEW_TIME_SHEET.name(),
            ROLE_VIEW_WEB_CLOCK.name()
    };

    private Map<String, Set<String>> emplIdToRoles = new HashMap<String, Set<String>>();


    @Override
    public Set<String> getHrsRoles(String emplId) {

        if (emplIdToRoles.containsKey(emplId)) {
            return emplIdToRoles.get(emplId);
        }


        Set<String> roles = new HashSet<String>();

        /* TODO: consider whether there's dependencies among the roles such that
         * it only makes sense to have some with others
         * and whether it's important for this demo DAO to do better than just random
         */
        for (String role : possible_roles) {
            if (random.nextBoolean()) {
                roles.add(role);
            }
        }

        this.emplIdToRoles.put(emplId, roles);

        return roles;
    }
}
