/**
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

package edu.wisc.cypress.dao.bnconf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.wisc.cypress.dao.benconf.RestBenefitConfirmationDao;
import edu.wisc.cypress.dm.MockProxyResponse;
import edu.wisc.hr.dm.benconf.BenefitConfirmation;
import edu.wisc.hr.dm.benconf.BenefitStatements;

/**
 * Test out the benefit confirmation. This is an integration test.
 * @author levett
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/integrationCypressTestContext.xml")
public class RestBenefitConfirmationDaoIT {
    @Autowired
    private RestBenefitConfirmationDao client;
    
    @Test
    public void testGetStatement() throws Exception {
        MockProxyResponse response = new MockProxyResponse();
        
        client.getBenefitConfirmation("6767981", "00282835", response);
        
        final byte[] content = response.getContentAsByteArray();
        assertNotNull(content);
        assertEquals(264, content.length);
    }
    
    @Test
    public void testGetStatements() throws Exception {
        final BenefitStatements benefitStatements = client.getBenefitConfirmations("00001234");
        assertNotNull(benefitStatements);
        final List<BenefitConfirmation> statements = benefitStatements.getBenefitConfirmations();
        assertNotNull(statements);
        assertEquals(1, statements.size());
    }
    
    @Test
    public void testNoStatements() throws Exception {
        final BenefitStatements benefitStatements = client.getBenefitConfirmations("00000000");
        assertNotNull(benefitStatements);
        final List<BenefitConfirmation> statements = benefitStatements.getBenefitConfirmations();
        assertNotNull(statements);
        assertEquals(0, statements.size());
    }
    
    @Test
    public void testBadEmplId() throws Exception {
        final BenefitStatements benefitStatements = client.getBenefitConfirmations("");
        assertNotNull(benefitStatements);
        final List<BenefitConfirmation> statements = benefitStatements.getBenefitConfirmations();
        assertNotNull(statements);
        assertEquals(0, statements.size());
    }
}
