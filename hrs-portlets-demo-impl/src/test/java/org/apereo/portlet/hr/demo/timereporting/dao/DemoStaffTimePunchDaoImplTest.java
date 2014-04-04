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

package org.apereo.portlet.hr.demo.timereporting.dao;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apereo.portlet.hr.HrPortletRuntimeException;
import org.apereo.portlet.hr.model.timereporting.TimePunchEntry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Description
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */

public class DemoStaffTimePunchDaoImplTest {

    DemoStaffTimePunchDaoImpl obj;
    String admin = "admin";
    PortletRequest request;

    @Before
    public void setUp() throws Exception {
        obj = new DemoStaffTimePunchDaoImpl();
        request = null;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetTimePunchEntries() throws Exception {
        List<TimePunchEntry> entries = obj.getTimePunchEntries(request, admin);
        assert entries.size() == 3;
        TimePunchEntry entry = entries.get(0);
        Assert.assertEquals("Entry 0 job code wrong", 123, entry.getJob().getJobCode());
        Assert.assertEquals("Entry 0 job title wrong", "Computer Technician", entry.getJob().getJobTitle());
        Assert.assertEquals("Entry 0 job description wrong", "Computer Technician Grade IT5", entry.getJob().getJobDescription());
        Assert.assertEquals("Entry 0 week time worked wrong", 1250, entry.getWeekTimeWorked());
        Assert.assertEquals("Entry 0 pay period time worked wrong", 1555, entry.getPayPeriodTimeWorked());
    }

    @Test
    public void testPunchInTimeClock() throws Exception {
        obj.punchInTimeClock(request, admin, 123, "127.0.0.1");
    }

    @Test
    public void testPunchInTimeClockTwice() {
        try {
            obj.punchInTimeClock(request, admin, 123, "127.0.0.1");
            obj.punchInTimeClock(request, admin, 123, "127.0.0.1");
            Assert.fail("Punch in did not throw error on 2nd punch in for same job code");
        } catch (HrPortletRuntimeException e) {
            // expected, all good.
        }
    }

    @Test
    public void testPunchOutTimeClockNoPunchIn() throws Exception {
        obj.punchOutTimeClock(request, admin, 123, "127.0.0.1");

    }

    @Test
    public void testPunchedInAndOutJob123() {
        List<TimePunchEntry> entries = obj.getTimePunchEntries(request, admin);
        TimePunchEntry entry = entries.get(0);
        Assert.assertEquals("Entry 0 job code is wrong", 123, entry.getJob().getJobCode());
        Assert.assertEquals("Entry 0 week time worked wrong", 1250, entry.getWeekTimeWorked());
        Assert.assertEquals("Entry 0 pay period time worked wrong", 1555, entry.getPayPeriodTimeWorked());
        obj.punchInTimeClock(request, admin, 123, "127.0.0.1");
        obj.punchOutTimeClock(request, admin, 123, "127.0.0.1");
        entries = obj.getTimePunchEntries(request, admin);
        entry = entries.get(0);
        // Punch out adds 5 minutes in demo mode
        Assert.assertEquals("Entry 0 week time worked wrong", 1255, entry.getWeekTimeWorked());
        Assert.assertEquals("Entry 0 pay period time worked wrong", 1560, entry.getPayPeriodTimeWorked());
    }
}
