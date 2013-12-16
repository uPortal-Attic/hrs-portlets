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
import edu.wisc.hr.dao.levstmt.StatementType;
import edu.wisc.hr.demo.support.PayPeriodGenerator;
import edu.wisc.hr.dm.levstmt.Report;
import edu.wisc.hr.dm.levstmt.SummarizedLeaveStatement;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ExtendedRestOperations;

import java.math.BigInteger;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Leave Statement DAO implementation that generates and remembers random leave data.
 */
@Repository
public class RandomLeaveStatementDao
    implements LeaveStatementDao {

    private final PayPeriodGenerator payPeriodGenerator = new PayPeriodGenerator();

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Random random = new Random();


    /**
     * Map from employee ID to Collection of summarized leave statements
     */
    private final Map<String, Collection<SummarizedLeaveStatement>> emplIdToSummarizedLeaveStatements =
            new HashMap<String, Collection<SummarizedLeaveStatement>>();

    @Override
    public Collection<SummarizedLeaveStatement> getLeaveStatements(String emplid) {

        if (emplIdToSummarizedLeaveStatements.containsKey(emplid)) {
            return emplIdToSummarizedLeaveStatements.get(emplid);
        }

        int howManyLeaveStatements = random.nextInt(20);

        List<String> payPeriodLabels = payPeriodGenerator.payPeriods(howManyLeaveStatements);

        Collection<SummarizedLeaveStatement> leaveStatements = new LinkedList<SummarizedLeaveStatement>();

        for (String payPeriodLabel : payPeriodLabels) {

            SummarizedLeaveStatement leaveStatement = new SummarizedLeaveStatement();

            BigInteger randomDocId = new BigInteger(Integer.toString(random.nextInt()));
            leaveStatement.setLeaveStatementDocId(randomDocId);

            leaveStatement.setLeaveStatementTitle("What's a leave statement title?");

            leaveStatement.setPayPeriod(payPeriodLabel);

            int howManyFurloughReports = random.nextInt(4);

            for (int i = 0; i < howManyFurloughReports; i++) {

                Report report = new Report();
                report.setTitle("What's a furlough report title?");

                BigInteger randomDocIdForFurlough = new BigInteger(Integer.toString(random.nextInt()));
                report.setDocId(randomDocIdForFurlough);

                leaveStatement.getLeaveFurloughReports().add(report);
            }

            int howManyMissingLeaveReports = random.nextInt(4);

            for (int i = 0; i < howManyMissingLeaveReports; i++) {

                Report report = new Report();
                report.setTitle("What's a missing leave report title?");

                BigInteger randomDocIdForFurlough = new BigInteger(Integer.toString(random.nextInt()));
                report.setDocId(randomDocIdForFurlough);

                leaveStatement.getMissingReports().add(report);
            }


            leaveStatements.add(leaveStatement);
        }

        this.emplIdToSummarizedLeaveStatements.put(emplid, leaveStatements);

        return leaveStatements;
    }

    @Override
    public void getLeaveStatement(String emplid, String docId, StatementType type, ExtendedRestOperations.ProxyResponse proxyResponse) {
        logger.warn("The demo implementation is unable to proxy leave statements.  " +
                "Attempted to proxy the statement with id {} for employee {} with type {} ", docId, emplid, type);
        throw new UnsupportedOperationException("Demo implementation does not support " +
                "proxying leave statement document.");
    }
}
