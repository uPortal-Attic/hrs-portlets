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

import edu.wisc.hr.dm.person.Job;

import java.util.Random;

/**
 * Utility class for generating random Jobs, including random job titles and mail drops, and
 * selecting random departments.
 */
public class RandomJobGenerator {

    Random random = new Random();

    private static final String[] TITLE_PREFIXES = {
            "Local", "Chief", "Assistant", "Managing", "Regional", "District", "Senior", "Deputy", "Acting",
            "Consulting", "Traveling", "Night", "General", "Specialist", "Open Source", "Cloud", "Village", "Social",
            "Virtual", "Human Resources", "Risk Mitigation",
    };

    private static final String[] TITLE_SUFFIXES = {
            "Bottle Washer", "Cat Herder", "Accountant", "Window Washer", "Dental Hygienist", "Director", "Manager",
            "Programmer General", "Programmer", "Developer", "Designer", "User Experience Designer", "Web Developer",
            "Janitor", "Auditor", "Watchman", "Mainframe Attendant", "Pianist", "Dancer", "Evangelist", "Concierge",
            "Punch Card Cleaner", "Vacuum Tube Vacuumer", "Blogger",
    };

    private static final String[] DEPARTMENTS = {
            "Portal Centric Services", "Productivity and Collaboration Solutions", "Technology and Planning",
            "Department of Information Technology", "Computer Science", "Maintenance",
            "Identity Management Consulting Services", "Department of Redundancy Department", "Pest Control",
            "Geology", "Biology", "College of Arts and Sciences", "Physics", "School of Engineering",
            "College of Letters", "Corporate Risk Mitigation"

    };

    private final RandomAddressGenerator randomAddressGenerator = new RandomAddressGenerator();


    /**
     * Generates a random Job.
     * @return a non-null Job
     */
    public Job randomJob() {

        Job job = new Job();

        job.setId(random.nextInt());
        job.setTitle( randomTitle() );
        job.setMailDrop(randomAddressGenerator.randomMailDrop());
        job.setDepartmentName( randomDepartmentName() );

        return job;
    }

    /**
     * Generates a random job title.
     * Examples: "Cloud Bottle Washer", "Acting Pianist", "Senior Evangelist"
     * @return a non-null String
     */
    public String randomTitle() {

        String prefix = TITLE_PREFIXES[ random.nextInt( TITLE_PREFIXES.length ) ];
        String suffix = TITLE_SUFFIXES[ random.nextInt( TITLE_SUFFIXES.length ) ];

        return prefix.concat(" ").concat(suffix);
    }


    /**
     * Generates a random department name drawn from DEPARTMENTS
     * @return a non-null String
     */
    public String randomDepartmentName() {
        return DEPARTMENTS [ random.nextInt ( DEPARTMENTS.length) ];
    }


}
