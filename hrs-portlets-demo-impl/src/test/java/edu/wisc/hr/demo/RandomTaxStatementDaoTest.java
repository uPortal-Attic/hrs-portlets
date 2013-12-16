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

import edu.wisc.hr.dao.taxstmt.TaxStatementDao;
import edu.wisc.hr.dm.taxstmt.TaxStatement;
import edu.wisc.hr.dm.taxstmt.TaxStatements;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for RandomTaxStatementDao.
 * Currently it documents the un-supported-ness of getting an actual individual tax statement,
 * demonstrates that the dao will consistently return the same data for a given employee ID,
 * and provides a utility method for printing some examples.
 */
public class RandomTaxStatementDaoTest {

    private TaxStatementDao dao = new RandomTaxStatementDao();

    @Test(expected = UnsupportedOperationException.class)
    public void gettingDocumentUnsupported() {
        dao.getTaxStatement("petro", "someDocId" , null);
    }

    @Test
    public void returnsSameTaxStatementsOnSubsequentCallForGivenEmplId() {

        TaxStatements petroTaxStatements = dao.getTaxStatements("petro");

        assertSame(petroTaxStatements, dao.getTaxStatements("petro"));
        assertNotSame(petroTaxStatements, dao.getTaxStatements("jacobson"));
    }


    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println(emplId + "'s Tax Statements");

            TaxStatements statementsBundle = dao.getTaxStatements(emplId);

            List<TaxStatement> statements = statementsBundle.getTaxStatements();

            for (TaxStatement statement : statements) {

                System.out.println("   " + statement.getName() + " : " + statement.getYear()
                        + " : " + statement.getFullTitle() + " : " + statement.getDocId());

            }

            System.out.println();

        }

    }

}
