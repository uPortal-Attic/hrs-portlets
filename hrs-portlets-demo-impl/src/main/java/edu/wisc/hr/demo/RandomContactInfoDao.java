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
import edu.wisc.hr.demo.support.RandomAddressGenerator;
import edu.wisc.hr.demo.support.RandomJobGenerator;
import edu.wisc.hr.demo.support.RandomNameGenerator;
import edu.wisc.hr.dm.person.Job;
import edu.wisc.hr.dm.person.PersonInformation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Random implementation of the contact info DAO API.
 * This is a DAO in that it remembers the random contact information it generates, in-memory.
 */
@Repository
public class RandomContactInfoDao
    implements ContactInfoDao {

    /**
     * Map from employee ID to PersonInformation.
     */
    private Map emplIdToPersonInformation = new HashMap<String, PersonInformation>();

    private static RandomNameGenerator RANDOM_NAME_GENERATOR = new RandomNameGenerator();

    private static RandomJobGenerator RANDOM_JOB_GENERATOR = new RandomJobGenerator();

    private static RandomAddressGenerator RANDOM_ADDRESS_GENERATOR = new RandomAddressGenerator();

    private Random random = new Random();

    @Override
    public PersonInformation getPersonalData(String emplId) {

        if (this.emplIdToPersonInformation.containsKey(emplId)) {
            return (PersonInformation) this.emplIdToPersonInformation.get(emplId);
        }

        PersonInformation personInformation = new PersonInformation();

        personInformation.setName(RANDOM_NAME_GENERATOR.randomName(emplId));
        personInformation.setEmail( emplId.concat("@entropy.edu") );

        Job primaryJob = RANDOM_JOB_GENERATOR.randomJob();
        personInformation.setPrimaryJob( primaryJob );

        personInformation.setHomeAddress( RANDOM_ADDRESS_GENERATOR.randomHomeAddress() );

        String department = primaryJob.getDepartmentName();
        personInformation.setOfficeAddress( RANDOM_ADDRESS_GENERATOR.randomOfficeAddress(department) );


        personInformation.setMadisonEmpl( random.nextBoolean() );
        personInformation.setOnVisa( random.nextBoolean() );


        this.emplIdToPersonInformation.put(emplId, personInformation);

        return personInformation;
    }
}
