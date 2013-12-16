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

import edu.wisc.hr.dao.ernstmt.EarningStatementDao;
import edu.wisc.hr.dm.ernstmt.EarningStatement;
import edu.wisc.hr.dm.ernstmt.EarningStatements;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for the earning statements dao.
 *
 * Offers a way to print some examples.
 */
public class RandomEarningStatementDaoTest {

    private EarningStatementDao dao = new RandomEarningStatementDao();

    @Test
    public void returnsSameEarningStatementOnSubsequentCallForSameEmplId() {

        EarningStatements statements = dao.getEarningStatements("petro");

        assertSame(statements, dao.getEarningStatements("petro"));
        assertNotSame(statements, dao.getEarningStatements("helwig"));

    }


    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {
            System.out.println(emplId + "'s Earning Statements");

            EarningStatements statementBundle = dao.getEarningStatements(emplId);

            List<EarningStatement> statements = statementBundle.getEarningStatements();

            for (EarningStatement statement : statements) {
                System.out.println(statement.getFullTitle() + " " + statement.getEarned() + " " + statement.getAmount
                        () + " " + statement.getPaid() + " " + statement.getDocId());
            }

            System.out.println();
        }
    }

}
