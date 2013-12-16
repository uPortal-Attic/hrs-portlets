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
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ExtendedRestOperations;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Random implementation of SabbaticalStatementDao API.
 */
@Repository
public class RandomSabbaticalStatementDao
    implements SabbaticalStatementDao {

    private Map<String, SabbaticalReports> emplIdToSabbaticalReports = new HashMap<String, SabbaticalReports>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Random random = new Random();

    @Override
    public SabbaticalReports getSabbaticalReports(String emplid) {

        if (this.emplIdToSabbaticalReports.containsKey(emplid)) {
            return emplIdToSabbaticalReports.get(emplid);
        }

        SabbaticalReports sabbaticalReports = new SabbaticalReports();

        int howManySabbaticalReports = random.nextInt(5);

        for (int i = 0; i < howManySabbaticalReports; i++) {

            SabbaticalReport sabbaticalReport = new SabbaticalReport();

            // TODO: make more realistic sabbatical reports

            sabbaticalReport.setFullTitle("What's a full title?");
            sabbaticalReport.setName("What's a name?");
            sabbaticalReport.setDocType("Type?");
            sabbaticalReport.setYear(new BigInteger("2013"));

            String randomDocId = Integer.toString(random.nextInt(Integer.MAX_VALUE));

            sabbaticalReport.setDocId(new BigInteger(randomDocId));

            sabbaticalReports.getSabbaticalReports().add(sabbaticalReport);

        }

        emplIdToSabbaticalReports.put(emplid, sabbaticalReports);

        return sabbaticalReports;
    }

    @Override
    public void getSabbaticalReport(String emplid, String docId, ExtendedRestOperations.ProxyResponse proxyResponse) {
        logger.warn("The demo implementation is unable to proxy sabbatical statements.  " +
                "Attempted to proxy the statement with id {} for employee {}.", docId, emplid);
        throw new UnsupportedOperationException("Demo implementation does not support " +
                "proxying sabbatical statement document.");
    }
}
