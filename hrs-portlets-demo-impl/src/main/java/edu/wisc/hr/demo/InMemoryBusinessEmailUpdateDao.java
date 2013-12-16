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
import edu.wisc.hr.dao.bnsemail.BusinessEmailUpdateNotifier;
import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dm.bnsemail.PreferredEmail;
import edu.wisc.hr.dm.person.PersonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of BusinessEmailUpdateDao defaults preferred email addresses to emplId@domain
 * but remembers in-memory updates to preferred email.
 *
 * This implementation makes no attempt to be thread-safe (it's a demo!).
 *
 * It depends on a ContactInfoDao (defaulting to the random data implementation) so as to provide consistent person
 * names for emplIds.  This is intended to be autowired such that this uses the same contact DAO as other HRS portlet
 * functionality.
 */
@Repository
public class InMemoryBusinessEmailUpdateDao
    implements BusinessEmailUpdateDao {

    private static final String DEMO_DOMAIN = "entropy.edu";

    private BusinessEmailUpdateNotifier businessEmailUpdateNotifier = new LoggingBusinessEmailUpdateNotifier();


    private ContactInfoDao contactInfoDao = new RandomContactInfoDao();

    /**
     * Map from emplId to preferred email address for exceptions to default.
     */
    private Map idToEmail = new HashMap<String, String>();

    @Override
    public PreferredEmail getPreferedEmail(String emplId) {

        if (emplId == null) {
            throw new IllegalArgumentException("Cannot get the preferred email of a user identified by a null emplId");
        }

        String preferredEmailAddress = emplId.concat("@").concat(DEMO_DOMAIN);

        if (idToEmail.containsKey(emplId)) {
            preferredEmailAddress = (String) idToEmail.get(emplId);
        }

        PreferredEmail preferredEmail = new PreferredEmail();
        preferredEmail.setEmail(preferredEmailAddress);
        preferredEmail.setEmplid(emplId);

        PersonInformation personalData = contactInfoDao.getPersonalData(emplId);

        preferredEmail.setName( personalData.getName() );
        preferredEmail.setMessage("Rain in Spain falls mainly on the plain.");

        return preferredEmail;
    }

    @Override
    public void updatePreferedEmail(String emplId, String newAddress) {

        if (newAddress == null) {
            throw new IllegalArgumentException("Cannot set preferred email address to null.");
        }

        if (emplId == null) {
            throw new IllegalArgumentException("Cannot update the preferred email address of " +
                    "a user identified by a null emplId");
        }

        String defaultAddress = emplId.concat(DEMO_DOMAIN);

        String oldAddress = defaultAddress;

        if (idToEmail.containsKey(emplId)) {
            oldAddress = (String) idToEmail.get(emplId);
        }

        if (newAddress.equals(defaultAddress)) {
            // setting preferred email to the default; simply drop any remembered mapping for this user
            idToEmail.remove(emplId);
        }


        idToEmail.put(emplId, newAddress);

        this.businessEmailUpdateNotifier.notifyEmailUpdated(oldAddress, newAddress);
    }

    public ContactInfoDao getContactInfoDao() {
        return contactInfoDao;
    }

    @Autowired
    public void setContactInfoDao(ContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }

}
