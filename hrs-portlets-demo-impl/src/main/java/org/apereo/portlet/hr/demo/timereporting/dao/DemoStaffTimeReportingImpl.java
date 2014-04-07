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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

import org.apereo.portlet.hr.dao.timereporting.StaffTimeReportingDao;
import org.apereo.portlet.hr.model.timereporting.JobCodeTime;
import org.apereo.portlet.hr.model.timereporting.JobDescription;
import org.apereo.portlet.hr.model.timereporting.LeaveSummary;
import org.apereo.portlet.hr.model.timereporting.PayPeriodDailyLeaveTimeSummary;
import org.apereo.portlet.hr.model.timereporting.TimePeriodEntry;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Description
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */
@Repository
public class DemoStaffTimeReportingImpl implements StaffTimeReportingDao {
    private static final int SATURDAY = 6;  // Day of week for Saturday, the start of the week for this
    private static final int DAYS_IN_PAY_PERIOD = 14;
    private static final int WORKED = 0;
    private static final int SICK = 201;
    private static final int VACATION = 202;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String jsonDataDir = "/data/demo/timereporting/";
    private String jsonDefaultInputFile = "default.json";
    private List<JobDescription> jobDescriptions;
    private Set<Integer> uneditableJobs;
    private Set<Integer> allUneditableJobs;

    // Map<emplId, List<JobCodeTime>
    private Map<String, List<JobCodeTime>> emplLeaveBalances = Collections.synchronizedMap(
            new HashMap<String, List<JobCodeTime>>());

    // Map<emplId, List<TimePeriodEntry>
    private Map<String, List<TimePeriodEntry>> emplWorkEntries = Collections.synchronizedMap(
            new HashMap<String, List<TimePeriodEntry>>());

    // Map<emplId, List<TimePeriodEntry>
    private Map<String, List<TimePeriodEntry>> emplLeaveEntries = Collections.synchronizedMap(
            new HashMap<String, List<TimePeriodEntry>>());

    DemoStaffTimeReportingImpl() {
        initJobDescriptions();
        initUneditableJobs();
    }

    private void initJobDescriptions() {
        List<JobDescription> jobDesc = new ArrayList<JobDescription>();
        jobDesc.add(new JobDescription(WORKED, "Hours", "Hours Worked"));
        jobDesc.add(new JobDescription(SICK, "Sick", "Sick Time"));
        jobDesc.add(new JobDescription(VACATION, "Vacation", "Vacation Time"));
        jobDescriptions = Collections.unmodifiableList(jobDesc);
    }

    // Add WORKED to the uneditable job code list, and all jobs to the allUneditable job code list
    private void initUneditableJobs() {
        Set<Integer> uneditable = new HashSet<Integer>();
        uneditable.add(WORKED);
        uneditableJobs = Collections.unmodifiableSet(uneditable);

        Set<Integer> allUneditable = new HashSet<Integer>();
        allUneditable.addAll(Arrays.asList(new Integer[] {WORKED, SICK, VACATION}));
        allUneditableJobs = Collections.unmodifiableSet(allUneditable);
    }

    // Initialize data for the employee ID if needed.
    private void initializeForEmployeeIfNeeded (String emplId) {
        // All employees have a leave balance and sick balance setup
        if (emplLeaveBalances.get(emplId) == null) {
            List<JobCodeTime> leaveBalances = new ArrayList<JobCodeTime>();
            leaveBalances.add(new JobCodeTime(SICK, 215*60+4)); // 215 hours, 4 min
            leaveBalances.add(new JobCodeTime(VACATION, 64*60+10)); //64:10
            emplLeaveBalances.put(emplId, leaveBalances);
        }

        // Add work entries for the employee.
        if (emplWorkEntries.get(emplId) == null) {
            LocalDate startDate = calculatePayperiodStartDate(new LocalDate());
            LocalDate tomorrow = new LocalDate().plusDays(1);
            List<TimePeriodEntry> items = new ArrayList<TimePeriodEntry>();
            // Create entries for 1 pay period back through current
            for (int i = -DAYS_IN_PAY_PERIOD; i < DAYS_IN_PAY_PERIOD; i++) {
                LocalDate date = startDate.plusDays(i);
                // For previous pay period, worked 7 hours a day. For current pay period, hours is based on
                // day of week + 15 minutes, up through today.  // Skip Sat and Sun since they by default don't display.
                int timeWorked = i < 0 ? 7 * 60 : date.getDayOfWeek()*60+15;
                if (date.getDayOfWeek() < 6 && date.isBefore(tomorrow)) {
                    items.add(new TimePeriodEntry(date, WORKED, timeWorked));
                }
            }
            emplWorkEntries.put(emplId, items);
        }

        if (emplLeaveEntries.get(emplId) == null) {
            emplLeaveEntries.put(emplId, new ArrayList<TimePeriodEntry>());
        }
    }

    private LocalDate calculatePayperiodStartDate(LocalDate date) {
        return date.minusDays(5).withDayOfWeek(SATURDAY);
    }

    @Override
    public PayPeriodDailyLeaveTimeSummary getLeaveHoursReported(PortletRequest request, String emplId, LocalDate dateInPayPeriod) {
        initializeForEmployeeIfNeeded(emplId);

        LocalDate startDate = calculatePayperiodStartDate(dateInPayPeriod);

        PayPeriodDailyLeaveTimeSummary summary = new PayPeriodDailyLeaveTimeSummary();
        summary.setPayPeriodStart(startDate);
        summary.setPayPeriodEnd(startDate.plusDays(DAYS_IN_PAY_PERIOD - 1));
        summary.setJobDescriptions(jobDescriptions);

        // If more than 1 pay period back or forward, leave cannot be entered.
        int dayDelta = Days.daysBetween(new LocalDate(), startDate).getDays();
        summary.setDisplayOnlyJobCodes(dayDelta <= DAYS_IN_PAY_PERIOD * -2 || dayDelta > DAYS_IN_PAY_PERIOD ?
                allUneditableJobs : uneditableJobs);
        summary.setTimePeriodEntries(getTimeEntries(emplId, startDate, summary.getPayPeriodEnd()));

        return summary;
    }

    // Return a list of all the work and leave time entries for the employee within the indicated date range
    private List<TimePeriodEntry> getTimeEntries (String emplId, LocalDate startDate, LocalDate endDate) {
        List<TimePeriodEntry> entries = entriesInDateRange(startDate, endDate, emplWorkEntries.get(emplId));
        entries.addAll(entriesInDateRange(startDate, endDate, emplLeaveEntries.get(emplId)));
        return entries;
    }

    // Return a list of entries within the indicate date range.
    private List<TimePeriodEntry> entriesInDateRange(LocalDate startDate, LocalDate endDate, List<TimePeriodEntry> entries) {
        LocalDate dayPriorToStart = startDate.minusDays(1);
        LocalDate dayAfterEnd = endDate.plusDays(1);
        List<TimePeriodEntry> validEntries = new ArrayList<TimePeriodEntry>();
        for (TimePeriodEntry entry : entries) {
            if (entry.getDate().isAfter(dayPriorToStart) && entry.getDate().isBefore(dayAfterEnd)) {
                validEntries.add(entry);
            }
        }
        return validEntries;
    }

    @Override
    public void updateLeaveTimeReported(PortletRequest request, String emplId, List<TimePeriodEntry> updatedTimesheet) {
        initializeForEmployeeIfNeeded(emplId);
        log.debug("Updating leave entries for employee ID {}", emplId);

        List<TimePeriodEntry> timeEntries = emplLeaveEntries.get(emplId);
        Map<String, TimePeriodEntry> entryMap = createMapOfTimeEntries(timeEntries);
        for (TimePeriodEntry update : updatedTimesheet) {
            TimePeriodEntry entry = entryMap.get(encodeJobAndDate(update.getJobCode(), update.getDate()));
            if ( entry != null) {
                entry.setTimeEntered(update.getTimeEntered());
            } else {
                timeEntries.add(update);
            }
        }
    }

    // Create a map of existing leave time entries based on job code and date
    private Map<String, TimePeriodEntry> createMapOfTimeEntries(List<TimePeriodEntry> entries) {
        Map<String, TimePeriodEntry> entryMap = new HashMap<String, TimePeriodEntry>();
        for (TimePeriodEntry entry : entries) {
            entryMap.put(encodeJobAndDate(entry.getJobCode(), entry.getDate()), entry);
        }
        return entryMap;
    }

    private static String encodeJobAndDate(int jobCode, LocalDate date) {
        return Integer.toString(jobCode) + "_" + date.toString();
    }

    /**
     * Returns the leave summary (sick, vacation, etc.) for the employee for whatever time period (year to date, quarter to date,
     * month to date, etc.) is appropriate for this institution's needs.
     * needs.
     *
     * @param request Portlet Request
     * @param emplId  Employee ID
     * @return Leave summary information for employee.
     */
    @Override
    public LeaveSummary getLeaveSummary(PortletRequest request, String emplId) {
        initializeForEmployeeIfNeeded(emplId);
        LeaveSummary summary = new LeaveSummary();
        summary.setJobDescriptions(removeTimeWorked(jobDescriptions));
        calculateLeaveBalances(emplId, summary);
        summary.setLeaveEarned(calculateEarned(summary.getLeaveBalance()));
        return summary;
    }

    private List<JobDescription> removeTimeWorked(List<JobDescription> jobDescriptions) {
        ArrayList jobs = new ArrayList();
        for (JobDescription job : jobDescriptions) {
            if (job.getJobCode() != WORKED) {
                jobs.add(job);
            }
        }
        return Collections.unmodifiableList(jobs);
    }

    // Set earned = balance / 4
    private Set<JobCodeTime> calculateEarned(Set<JobCodeTime> currentBalance) {
        HashSet<JobCodeTime> earned = new HashSet<JobCodeTime>();
        Iterator<JobCodeTime> iterator = currentBalance.iterator();
        while (iterator.hasNext()) {
            JobCodeTime item = iterator.next();
            earned.add(new JobCodeTime(item.getJobCode(), item.getTime()/4));
        }
        return earned;
    }

    // Calculate the current leave balance taking into account the employee's balance minus all existing time
    // entries for that job code.
    private Set<JobCodeTime> calculateLeaveBalances(String emplId, LeaveSummary summary) {
        Set<JobCodeTime> currentBalances = new HashSet<JobCodeTime>();
        Set<JobCodeTime> leaveTaken = new HashSet<JobCodeTime>();
        Set<JobCodeTime> leaveEarned = new HashSet<JobCodeTime>();
        for (JobCodeTime leaveBalance : emplLeaveBalances.get(emplId)) {
            int leaveAmount = calculateCurrentLeaveTaken(leaveBalance.getJobCode(), emplLeaveEntries.get(emplId));
            leaveTaken.add(new JobCodeTime(leaveBalance.getJobCode(), leaveAmount));

            int currentBalance = leaveBalance.getTime() - leaveAmount;
            currentBalances.add(new JobCodeTime(leaveBalance.getJobCode(), currentBalance));

            // Set leave earned to balance / 4
            leaveEarned.add(new JobCodeTime(leaveBalance.getJobCode(), leaveBalance.getTime() / 4));
        }
        summary.setLeaveBalance(currentBalances);
        summary.setLeaveTaken(leaveTaken);
        summary.setLeaveEarned(leaveEarned);
        return currentBalances;
    }

    // Calculate the amount of leave taken.
    private int calculateCurrentLeaveTaken(int jobCode, List<TimePeriodEntry> entries) {
        int leaveTaken = 0;
        for (TimePeriodEntry entry : entries) {
            if (entry.getJobCode() == jobCode) {
                leaveTaken += entry.getTimeEntered();
            }
        }
        return leaveTaken;
    }
}
