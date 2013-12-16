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


import edu.wisc.hr.dao.url.HrsUrlDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static edu.wisc.hr.dao.url.HrsUrl.*;

/**
 * Random implementation of the HrsUrlDao API.
 */
@Repository
public class RandomHrsUrlDao
    implements HrsUrlDao {

    Random random = new Random();

    /**
     * Some example URLs to display
     */
    private static Map<String, String> LABELS_TO_URLS = new HashMap<String, String>();

    static {

        // URLS actually surfaced in the UI
        LABELS_TO_URLS.put(APPROVE_ABSENCE.getCode(), "http://xkcd.com/1235/");
        LABELS_TO_URLS.put(APPROVE_PAYABLE_TIME.getCode(), "http://xkcd.com/980/");
        LABELS_TO_URLS.put(BENEFITS_ENROLLMENT.getCode(), "http://xkcd.com/773/");
        LABELS_TO_URLS.put(BENEFITS_SUMMARY.getCode(), "http://xkcd.com/657/");
        LABELS_TO_URLS.put(DEPENDENT_COVERAGE.getCode(), "http://xkcd.com/674/");
        LABELS_TO_URLS.put(DEPENDENT_INFORMATION.getCode(), "http://xkcd.com/327/");
        LABELS_TO_URLS.put(OPEN_ENROLLMENT_HIRE_EVENT.getCode(), "http://xkcd.com/1094/");
        LABELS_TO_URLS.put(PERSONAL_INFORMATION.getCode(), "http://xkcd.com/517/");
        LABELS_TO_URLS.put(REQUEST_ABSENCE.getCode(), "http://xkcd.com/612/");
        LABELS_TO_URLS.put(TIME_MANAGEMENT.getCode(), "http://xkcd.com/874/");
        LABELS_TO_URLS.put(TIMESHEET.getCode(), "http://xkcd.com/1205/");
        LABELS_TO_URLS.put(UPDATE_TSA_DEDUCTIONS.getCode(), "http://xkcd.com/951/");
        LABELS_TO_URLS.put(PAYABLE_TIME_DETAIL.getCode(), "http://xkcd.com/808/");
        LABELS_TO_URLS.put(TIME_EXCEPTIONS.getCode(), "http://xkcd.com/162/");
        LABELS_TO_URLS.put(WEB_CLOCK.getCode(), "http://time.gov/");

        // other URLs just because we can (since the portlet ought to cope
        // even if extra URLs are available in the map)
        LABELS_TO_URLS.put("wiki",
                "https://wiki.jasig.org/display/PLT/Human+Resources+Dashboard+Portlets");
        LABELS_TO_URLS.put("Apereo",
                "http://www.apereo.org/");
        LABELS_TO_URLS.put("HRS Portlets Source Code",
                "https://github.com/Jasig/hrs-portlets");
        LABELS_TO_URLS.put("Fitocracy",
                "http://www.fitocracy.com");
        LABELS_TO_URLS.put("HabitRPG",
                "https://habitrpg.com");
        LABELS_TO_URLS.put("Checklist Manifesto",
                "https://www.goodreads.com/book/show/6667514-the-checklist-manifesto");
        LABELS_TO_URLS.put("Bucky Badger",
                "http://en.wikipedia.org/wiki/Bucky_Badger");
        LABELS_TO_URLS.put("Git Commit",
                "http://xkcd.com/1296/");
        LABELS_TO_URLS.put("Serial Comma",
                "http://en.wikipedia.org/wiki/Serial_comma");


    }



    @Override
    public Map<String, String> getHrsUrls() {

        return LABELS_TO_URLS;
    }
}
