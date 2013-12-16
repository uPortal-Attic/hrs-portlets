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
import edu.wisc.hr.dm.person.PersonInformation;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Demonstrates random contact info generation.
 */
public class RandomContactInfoDaoTest {

    private ContactInfoDao dao = new RandomContactInfoDao();

    @Test
    public void returnsSameContactInfoOnSubsequentCallForEmplId() {

        PersonInformation contactInfo = dao.getPersonalData("petro");

        assertSame(contactInfo, dao.getPersonalData("petro"));
        assertNotSame(contactInfo, dao.getPersonalData("treige"));

    }


    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };


        for (String emplId : emplIds) {

            System.out.println(emplId + "'s Contact Info");

            PersonInformation contactInfo = dao.getPersonalData(emplId);

            System.out.println("Name: " + contactInfo.getName());
            System.out.println("Email: " + contactInfo.getEmail());

            System.out.println("Home address: " + contactInfo.getHomeAddress());
            System.out.println("Office address: " + contactInfo.getOfficeAddress());

            System.out.println("Primary job: " + contactInfo.getPrimaryJob());
            System.out.println("Job map: " + contactInfo.getJobs());

            System.out.println();

        }

    }



}
