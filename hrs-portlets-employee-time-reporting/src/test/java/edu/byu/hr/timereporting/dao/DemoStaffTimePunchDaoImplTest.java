/**
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

package edu.byu.hr.timereporting.dao;

import java.util.List;

import edu.byu.hr.HrPortletRuntimeException;
import edu.byu.hr.model.timereporting.TimePunchEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Description
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class DemoStaffTimePunchDaoImplTest {

    DemoStaffTimePunchDaoImpl obj;
    String admin = "admin";

    @Before
    public void setUp() throws Exception {
        obj = new DemoStaffTimePunchDaoImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetTimePunchEntries() throws Exception {
        List<TimePunchEntry> entries = obj.getTimePunchEntries(admin);
        assert entries.size() == 3;
        TimePunchEntry entry = entries.get(0);
        assertEquals("Entry 0 job code wrong", 123, entry.getJob().getJobCode());
        assertEquals("Entry 0 job title wrong", "Computer Technician", entry.getJob().getJobTitle());
        assertEquals("Entry 0 job description wrong", "Computer Technician Grade IT5", entry.getJob().getJobDescription());
        assertEquals("Entry 0 week time worked wrong", 1250, entry.getWeekTimeWorked());
        assertEquals("Entry 0 pay period time worked wrong", 1555, entry.getPayPeriodTimeWorked());
    }

    @Test
    public void testPunchInTimeClock() throws Exception {
        obj.punchInTimeClock(admin, 123, "127.0.0.1");
    }

    @Test
    public void testPunchInTimeClockTwice() {
        try {
            obj.punchInTimeClock(admin, 123, "127.0.0.1");
            obj.punchInTimeClock(admin, 123, "127.0.0.1");
            fail("Punch in did not throw error on 2nd punch in for same job code");
        } catch (HrPortletRuntimeException e) {
            // expected, all good.
        }
    }

    @Test
    public void testPunchOutTimeClockNoPunchIn() throws Exception {
        obj.punchOutTimeClock(admin, 123, "127.0.0.1");

    }

    @Test
    public void testPunchedInAndOutJob123() {
        List<TimePunchEntry> entries = obj.getTimePunchEntries(admin);
        TimePunchEntry entry = entries.get(0);
        assertEquals("Entry 0 job code is wrong", 123, entry.getJob().getJobCode());
        assertEquals("Entry 0 week time worked wrong", 1250, entry.getWeekTimeWorked());
        assertEquals("Entry 0 pay period time worked wrong", 1555, entry.getPayPeriodTimeWorked());
        obj.punchInTimeClock(admin, 123, "127.0.0.1");
        obj.punchOutTimeClock(admin, 123, "127.0.0.1");
        entries = obj.getTimePunchEntries(admin);
        entry = entries.get(0);
        // Punch out adds 5 minutes in demo mode
        assertEquals("Entry 0 week time worked wrong", 1255, entry.getWeekTimeWorked());
        assertEquals("Entry 0 pay period time worked wrong", 1560, entry.getPayPeriodTimeWorked());
    }
}
