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
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ExtendedRestOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Random implementation of TaxStatementDao API.
 */
@Repository
public class RandomTaxStatementDao
    implements TaxStatementDao {

    private Map<String, TaxStatements> emplIdToTaxStatements = new HashMap<String, TaxStatements>();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Random random = new Random();

    @Override
    public TaxStatements getTaxStatements(String emplid) {

        if (this.emplIdToTaxStatements.containsKey(emplid)) {
            return this.emplIdToTaxStatements.get(emplid);
        }

        TaxStatements taxStatements = new TaxStatements();

        int howManyTaxStatements = random.nextInt(20);

        for (int i = 0; i < howManyTaxStatements; i++) {

            TaxStatement taxStatement = new TaxStatement();

            taxStatement.setFullTitle("What's a title?");
            taxStatement.setName("What's a name?");

            String randomDocId = Integer.toString(random.nextInt(Integer.MAX_VALUE));
            taxStatement.setDocId(new BigInteger(randomDocId));

            taxStatement.setYear(new BigInteger("2013"));

            taxStatements.getTaxStatements().add(taxStatement);

        }


        this.emplIdToTaxStatements.put(emplid, taxStatements);

        return taxStatements;





    }

    @Override
    public void getTaxStatement(String emplid, String docId, ExtendedRestOperations.ProxyResponse proxyResponse) {
        logger.warn("The demo implementation is unable to proxy sabbatical statements.  " +
                "Attempted to proxy the statement with id {} for employee {}.", docId, emplid);
        throw new UnsupportedOperationException("Demo implementation does not support " +
                "proxying sabbatical statement document.");
    }
}
