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

import edu.wisc.hr.dao.bnsemail.BusinessEmailUpdateDao;
import edu.wisc.hr.dm.bnsemail.PreferredEmail;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the in-memory business email update DAO.
 */
public class InMemoryBusinessEmailUpdateDaoTest {

    private BusinessEmailUpdateDao businessEmailUpdateDao = new InMemoryBusinessEmailUpdateDao();

    @Test
    public void getPreferredEmailGetsAnEmailAddress() {

        PreferredEmail preferredEmail = businessEmailUpdateDao.getPreferedEmail("awp9");

        assertNotNull("Should not have returned a null PreferredEmail object", preferredEmail);

        String emailAddress = preferredEmail.getEmail();

        assertNotNull("Should not have returned a null email address within the PreferredEMail object", emailAddress);

        assertTrue("Email address should have contained an at sign but instead was [" + emailAddress + "]",
                emailAddress.indexOf('@') > 0);

        assertEquals("awp9", preferredEmail.getEmplid());


    }

    @Test
    public void updatesToEmailAddressAreRemembered() {

        PreferredEmail preferredEmail = businessEmailUpdateDao.getPreferedEmail("awp9");
        PreferredEmail preferredEmailAgain = businessEmailUpdateDao.getPreferedEmail("awp9");

        assertEquals(preferredEmail, preferredEmailAgain);

        // notice to spammers trawling source code: the following email address is not real. Gotcha!
        businessEmailUpdateDao.updatePreferedEmail("awp9", "andrew.petro@doit.wisc.edu");

        PreferredEmail preferredEmailAfterUpdate = businessEmailUpdateDao.getPreferedEmail("awp9");

        assertEquals("andrew.petro@doit.wisc.edu", preferredEmailAfterUpdate.getEmail());

    }


    /**
     * Utility method for printing examples of generated email addresses.
     */
    @Ignore
    public void printSomeExamples() {

        String[] usernames = {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : usernames) {
            PreferredEmail preferredEmail = businessEmailUpdateDao.getPreferedEmail(emplId);

            System.out.println(preferredEmail);
        }

    }

}
