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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.wisc.hr.dao.ernstmt.EarningStatementDao;
import edu.wisc.hr.demo.support.PayPeriodGenerator;
import edu.wisc.hr.dm.ernstmt.EarningStatement;
import edu.wisc.hr.dm.ernstmt.EarningStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ExtendedRestOperations;

/**
 * Generates and remembers zero to nineteen earning statements for each employee.
 */
@Repository
public class RandomEarningStatementDao
    implements EarningStatementDao {

    private Map<String, EarningStatements> emplIdToEarningStatement = new HashMap<String, EarningStatements>();

    private PayPeriodGenerator payPeriodGenerator = new PayPeriodGenerator();

    private Random random = new Random();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public EarningStatements getEarningStatements(String emplid) {

        if (emplIdToEarningStatement.containsKey(emplid)) {
            return emplIdToEarningStatement.get(emplid);
        }

        EarningStatements earningStatements = new EarningStatements();

        int howManyEarningStatements = random.nextInt(16) + 4;

        // "earned" is the date period in which earned, as in
        // "11/03/2013 - 11/16/2013" . Get one such label for each earning statement
        List<String> payPeriodLabels = payPeriodGenerator.payPeriods(howManyEarningStatements);



        for (String periodLabel : payPeriodLabels) {
            EarningStatement earningStatement = new EarningStatement();

            int randomDocId = random.nextInt(Integer.MAX_VALUE);
            BigInteger randomDocIdAsBigInteger = new BigInteger(Integer.toString(randomDocId));
            earningStatement.setDocId(randomDocIdAsBigInteger);

            int randomDollars = random.nextInt(3000);
            int randomDimes = random.nextInt(10);
            int randomPennies = random.nextInt(10);

            // TODO: use a format string instead
            String amountString = "$".concat(Integer.toString(randomDollars)).concat(".").
                    concat(Integer.toString(randomDimes)).concat(Integer.toString(randomPennies));

            earningStatement.setAmount(amountString);

            // "earned" is the pay period
            earningStatement.setEarned(periodLabel);

            earningStatement.setFullTitle("What is a full title?");
            earningStatement.setPaid("Paid?");

            earningStatements.getEarningStatements().add(earningStatement);
        }

        this.emplIdToEarningStatement.put(emplid, earningStatements);

        return earningStatements;
    }

    @Override
    public void getEarningStatement(String emplid, String docId, ExtendedRestOperations.ProxyResponse proxyResponse) {
        logger.warn("The demo implementation is unable to proxy earning statements.  " +
                "Attempted to proxy the statement with id {} for employee {} in {} mode", docId, emplid);
        throw new UnsupportedOperationException("Demo implementation does not support " +
                "proxying earning statement document.");
    }
}
