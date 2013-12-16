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

import edu.wisc.hr.dao.sabstmt.SabbaticalStatementDao;
import edu.wisc.hr.dm.sabstmt.SabbaticalReport;
import edu.wisc.hr.dm.sabstmt.SabbaticalReports;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * This wants to be a unit test for RandomSabbaticalStatementDao.
 * Demonstrates that the dao consistent returns the same data for subsequent calls about the same employee id.
 * Offers a utility method to print some examples.
 */
public class RandomSabbaticalStatementDaoTest {

    private SabbaticalStatementDao dao = new RandomSabbaticalStatementDao();


    @Test
    public void returnsSameReportsOnSubsequentCallForGivenEmplId() {

        SabbaticalReports petroReports = dao.getSabbaticalReports("petro");

        assertSame(petroReports, dao.getSabbaticalReports("petro"));
        assertNotSame(petroReports, dao.getSabbaticalReports("seibold"));

    }

    @Ignore
    public void printSomeExamples() {

        String[] emplIds =  {
                "helwig", "levett", "petro", "vertein"
        };

        for (String emplId : emplIds) {

            System.out.println(emplId + "'s sabbatical reports:");

            SabbaticalReports sabbaticalReportsBundle = dao.getSabbaticalReports(emplId);

            List<SabbaticalReport> reports = sabbaticalReportsBundle.getSabbaticalReports();

            for (SabbaticalReport report : reports) {

                System.out.println("   " + report.getName() + " : " + report.getDocType() + " : " + report.getYear() +
                        " : " + report.getFullTitle() + " : " + report.getDocId());

            }

            System.out.println();

        }

    }

}
