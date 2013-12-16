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

import edu.wisc.hr.dao.benstmt.BenefitStatementDao;
import edu.wisc.hr.dm.benstmt.BenefitStatement;
import edu.wisc.hr.dm.benstmt.BenefitStatements;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Unit test for RandomBenefitStatementDao.
 * Demonstrates that getting a document is unsupported.
 * Demonstates that subsequent calls for a given employee id return consistent data.
 * Offers a utility method for printing examples.
 */
public class RandomBenefitStatementDaoTest {

    private BenefitStatementDao dao = new RandomBenefitStatementDao();

    @Test(expected = UnsupportedOperationException.class)
    public void gettingDocumentUnsupported() {
        dao.getBenefitStatement("petro", 2013, "390923290902", "mode", null);
    }

    @Test
    public void returnsSameBenefitStatementsOnSubsequentCallForSameEmplId() {

        BenefitStatements statements = dao.getBenefitStatements("petro");

        assertSame(statements, dao.getBenefitStatements("petro"));
        assertNotSame(statements, dao.getBenefitStatements("cudd"));

    }

    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println("Benefit statements for [" + emplId + "]");

            BenefitStatements statementsBundle = dao.getBenefitStatements(emplId);

            List<BenefitStatement> benefitStatements = statementsBundle.getBenefitStatements();

            for (BenefitStatement statement : benefitStatements) {

                System.out.println(statement);

            }

            System.out.println();
        }

    }
}
