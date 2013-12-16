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

import java.util.Random;

/**
 * Naive random name generator.
 */
public class RandomNameGenerator {

    Random random = new Random();

    private static final String[] FIRST_NAMES = {
            "Andrew", "Anne", "Bill", "Blake", "Beth", "Bruce", "Bucky", "Charles", "Claire", "Doug", "Dolores",
            "Ernest", "Elizabeth", "Francis", "Fiona", "George", "Georgia", "Harold", "Harriet",
            "Henry", "Ichabod", "Iris", "Jacob", "James", "John", "Jen", "Jessie", "Jessica", "Jerome", "Joseph",
            "Karl", "Kara", "Lee", "Lewis", "Laura", "Mark", "Mandy", "Matt", "Neil", "Nelly", "Orin", "Ophelia",
            "Patrick", "Penelope", "Randall", "Rachel", "Sam", "Samuel", "Samantha", "Sloane", "Timothy", "Tara",
            "Ursula", "Victor", "Victoria", "Wendell", "William", "Williamette", "Xavier", "Yvonne", "Zander" };

    private static final String[] LAST_NAMES = {
            "Blakley", "Bourey", "Bramhall", "Dalquist", "Gilbert", "Lewis", "Levett", "Lonas", "McCallum", "Newman",
            "Petro", "Sherman", "Stiles", "Swap", "Thompson", "Vertein", "Walker", "Wills"
    };


    /**
     * Generates a random name consisting of a first name a space and a last name.
     * @return a non-null String
     */
    public String randomName() {

        String firstName = FIRST_NAMES[ random.nextInt( FIRST_NAMES.length ) ];
        String lastName = LAST_NAMES[ random.nextInt( LAST_NAMES.length ) ];

        return firstName.concat(" ").concat(lastName);
    }

    /**
     * Generates names where the last name is the provided username but with initial capitialization.
     *
     * @param username
     * @return
     */
    public String randomName(String username) {

        String firstName = FIRST_NAMES[ random.nextInt( FIRST_NAMES.length ) ];

        String lastName = capitalize(username);

        return firstName.concat(" ").concat(lastName);

    }


    public String capitalize(String lowercase) {
        return lowercase.substring(0,1).toUpperCase().concat(
                lowercase.substring(1, lowercase.length())
        );
    }
}
