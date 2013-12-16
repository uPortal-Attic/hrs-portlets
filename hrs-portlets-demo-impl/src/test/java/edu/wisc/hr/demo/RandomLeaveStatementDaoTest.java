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

import edu.wisc.hr.dao.levstmt.LeaveStatementDao;
import edu.wisc.hr.dm.levstmt.Report;
import edu.wisc.hr.dm.levstmt.SummarizedLeaveStatement;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for RandomLeaveStatementDao.
 * Demonstrates that the dao consistently returns the same data for a given employee id.
 * Also offers a utility method for printing some examples.
 */
public class RandomLeaveStatementDaoTest {

    private LeaveStatementDao dao = new RandomLeaveStatementDao();

    @Test
    public void returnsSameLeaveStatementOnSubsequentCallForGivenEmplId() {

        Collection<SummarizedLeaveStatement> statements = dao.getLeaveStatements("petro");
        assertSame(statements, dao.getLeaveStatements("petro"));
        assertNotSame(statements, dao.getLeaveStatements("levett"));

    }

    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };


        for (String emplId : emplIds) {
            System.out.println(emplId + "'s Leave Statements:");

            Collection<SummarizedLeaveStatement> statements = dao.getLeaveStatements(emplId);

            for (SummarizedLeaveStatement statement : statements) {

                System.out.println(" For pay period : " + statement.getPayPeriod());

                List<Report> furloughReports = statement.getLeaveFurloughReports();
                System.out.println(statement.getLeaveFurloughReports());
                System.out.println(statement.getMissingReports());

            }

            System.out.println();
        }

    }

}
