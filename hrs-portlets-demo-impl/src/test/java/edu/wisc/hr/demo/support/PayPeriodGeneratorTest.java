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

package edu.wisc.hr.demo.support;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for PayPeriodGenerator.
 *
 *
 */
public class PayPeriodGeneratorTest {

    private PayPeriodGenerator payPeriodGenerator = new PayPeriodGenerator();

    @Test
    public void generatesRequestedNumberOfPayPeriods() {

        List<String> payPeriods = payPeriodGenerator.payPeriods(0);
        assertEquals(payPeriods.size(), 0);

        payPeriods = payPeriodGenerator.payPeriods(10);
        assertEquals(payPeriods.size(), 10);

        payPeriods = payPeriodGenerator.payPeriods(20);
        assertEquals(payPeriods.size(), 20);
    }

    @Test
    public void generatesNonBlankPayPeriods() {

        List<String> payPeriods = payPeriodGenerator.payPeriods(10);

        for (String payPeriod : payPeriods) {
            String trimmedPayPeriod = payPeriod.trim();
            assertNotEquals("Pay period should have contained non-whitespace characters.", trimmedPayPeriod, "");
        }

    }

    @Test
    public void generatesUniquePayPeriods() {

        List<String> payPeriods = payPeriodGenerator.payPeriods(10);

        Set<String> seenPayPeriods = new HashSet<String>();

        for (String payPeriod : payPeriods) {
            assertTrue("Pay period should not have been a duplicate of a previous pay period in the list",
                    seenPayPeriods.add(payPeriod));
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionForNegativeNumberOfPayPeriods() {
        payPeriodGenerator.payPeriods(-3);
    }

    /**
     * Utility method for printing out some pay periods to examine.
     */
    @Ignore
    public void printSomeExamples() {

        List<String> payPeriods = payPeriodGenerator.payPeriods(10);


        for (String payPeriod : payPeriods) {
            System.out.println(payPeriod);
        }
    }


}
