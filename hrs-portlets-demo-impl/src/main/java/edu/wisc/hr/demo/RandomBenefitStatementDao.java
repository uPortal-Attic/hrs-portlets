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
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ExtendedRestOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Random demo implementation of benefit statement DAO.
 * DAO in the sense that it remembers generated benefit statements in memory.
 */
@Repository
public class RandomBenefitStatementDao
    implements BenefitStatementDao {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, BenefitStatements> emplIdToBenefitStatements = new HashMap<String, BenefitStatements>();

    private Random random = new Random();


    @Override
    public BenefitStatements getBenefitStatements(String emplid) {

        if (emplIdToBenefitStatements.containsKey(emplid)) {
            return emplIdToBenefitStatements.get(emplid);
        }

        int howManyBenefitStatements = random.nextInt(20);

        BenefitStatements benefitStatements = new BenefitStatements();

        for (int i = 0; i < howManyBenefitStatements; i++) {

            BenefitStatement benefitStatement = new BenefitStatement();

            int year = 2013 - i;
            String yearString = Integer.toString(year);

            benefitStatement.setDocId( new BigInteger(Integer.toString( random.nextInt())));
            benefitStatement.setDocType("WhatsADocType?");
            benefitStatement.setFullTitle("A Really Important Benefit Statement for ".concat(yearString));
            benefitStatement.setName("Benefit Statement ".concat(yearString));
            benefitStatement.setYear(new BigInteger(yearString));

            benefitStatements.getBenefitStatements().add(benefitStatement);

        }

        this.emplIdToBenefitStatements.put(emplid, benefitStatements);

        return benefitStatements;
    }

    @Override
    public void getBenefitStatement(String emplid, int year, String docId, String mode,
                                    ExtendedRestOperations.ProxyResponse proxyResponse) {
        logger.warn("The demo implementation is unable to proxy benefit statements.  " +
                "Attempted to proxy the {} statement with id {} for employee {} in {} mode", year, docId, emplid, mode);
        throw new UnsupportedOperationException("Demo implementation does not support " +
                "proxying benefit statement document.");
    }
}
