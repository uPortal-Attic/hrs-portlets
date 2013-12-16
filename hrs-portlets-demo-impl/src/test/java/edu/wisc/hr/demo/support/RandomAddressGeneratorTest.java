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

import edu.wisc.hr.dm.person.HomeAddress;
import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for random address generator
 */
public class RandomAddressGeneratorTest {

    private RandomAddressGenerator randomAddressGenerator = new RandomAddressGenerator();

    @Test
    public void generatesHomeAddress() {

        HomeAddress homeAddress = randomAddressGenerator.randomHomeAddress();

        assertNotNull("Generated address should not have been null", homeAddress);


    }


    @Ignore
    public void printSomeExamples() {

        for (int i = 0; i < 10; i++) {
            System.out.println(randomAddressGenerator.randomHomeAddress());
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(randomAddressGenerator.randomOfficeAddress("Pest Control"));
        }


    }

}
