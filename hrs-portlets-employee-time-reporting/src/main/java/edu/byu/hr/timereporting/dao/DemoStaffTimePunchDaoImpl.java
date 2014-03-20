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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.hr.HrPortletRuntimeException;
import edu.byu.hr.dao.timereporting.StaffTimePunchDao;
import edu.byu.hr.model.timereporting.JobDescription;
import edu.byu.hr.model.timereporting.TimePunchEntry;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * DAO Implementation providing demo data for the Staff Time punch portlet.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */
@Repository
public class DemoStaffTimePunchDaoImpl implements StaffTimePunchDao {
    private static final int PUNCH_OUT_INCREMENT = 5;  // 5 MINUTES

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String jsonDataDir = "/data/demo/timepunch/";
    private String jsonDefaultInputFile = "default.json";

    // Map <UserId, List<TimePunchEntry>>
    private Map<String, List<TimePunchEntry>> timePunchDataCache =
            Collections.synchronizedMap(new HashMap<String, List<TimePunchEntry>>());

    // Map<UserId, Map<jobCode, punchedIn>
    private Map<String, Map<Integer, Boolean>> punchedInList = Collections.synchronizedMap(new HashMap<String, Map<Integer, Boolean>>());

    public String getJsonDataDir() {
        return jsonDataDir;
    }

    public void setJsonDataDir(String jsonDataDir) {
        this.jsonDataDir = jsonDataDir;
    }

    public String getJsonDefaultInputFile() {
        return jsonDefaultInputFile;
    }

    public void setJsonDefaultInputFile(String jsonDefaultInputFile) {
        this.jsonDefaultInputFile = jsonDefaultInputFile;
    }

    /**
     * Returns the existing time punch data, or loads from a file based on the employeeId.
     * @param employeeId employeeId
     * @return existing time punch data for employee.
     */
    private List<TimePunchEntry> getTimePunchData(String employeeId) {
        List<TimePunchEntry> timePunchData = timePunchDataCache.get(employeeId);
        String filename = jsonDataDir + employeeId + ".json";
        if (timePunchData == null) {
            InputStream source = getClass().getResourceAsStream(filename);
            if (source == null) {
                filename = jsonDataDir + jsonDefaultInputFile;
                source = getClass().getResourceAsStream(filename);
            }
            log.debug("Creating new time punch data for employee {} from file {}", employeeId, filename);
            try {
                timePunchData = new ArrayList<TimePunchEntry>();

                ObjectMapper mapper = new ObjectMapper();
                ArrayNode json = mapper.readValue(source, ArrayNode.class);
                for (JsonNode timePunchEntry : json) {

                    int jobCode = timePunchEntry.path("jobId").getIntValue();
                    String jobTitle = timePunchEntry.path("jobTitle").getTextValue();
                    String jobDescription = timePunchEntry.path("jobDescription").getTextValue();
                    int weekTime = timePunchEntry.path("weekTime").asInt();
                    int periodTime = timePunchEntry.path("periodTime").asInt();
                    JobDescription job = new JobDescription(jobCode, jobTitle, jobDescription);
                    timePunchData.add(new TimePunchEntry(job, weekTime, periodTime, false));
                }

            } catch (Exception e) {
                log.error("Failed to load TimePunchEntry collection from file " + filename, e);
            }
            timePunchDataCache.put(employeeId, timePunchData);

            // Also create the employee's punch-in times map
            Map<Integer, Boolean> punchIns = new HashMap<Integer, Boolean>();
            for (TimePunchEntry entry : timePunchData) {
                punchIns.put(entry.getJob().getJobCode(), null);
            }
            punchedInList.put(employeeId, Collections.synchronizedMap(punchIns));
        }
        return timePunchData;
    }

    @Override
    public List<TimePunchEntry> getTimePunchEntries(String emplId) {
        List<TimePunchEntry> jobEntries = getTimePunchData(emplId);
        Map<Integer, Boolean> punchedInJobs = punchedInList.get(emplId);
        // Update the punched-in status
        for (TimePunchEntry jobEntry : jobEntries) {
            jobEntry.setPunchedIn(punchedInJobs.get(jobEntry.getJob().getJobCode()) != null);
        }
        return jobEntries;
    }



    @Override
    public synchronized void punchInTimeClock(String emplId, int jobCode, String clientIP) {
        getTimePunchData(emplId);  // For Demo mode and junit tests, make sure the database is initialized
        log.debug("Punching in employee {} for job Code {} from IP {}", emplId, jobCode, clientIP);
        Map<Integer,Boolean> punchedInJobs = punchedInList.get(emplId);
        if (punchedInJobs.get(jobCode) != null) {
            throw new HrPortletRuntimeException("Employee " + emplId + " is already punched in for "
                    + getJobDescription(emplId, jobCode));
        }
        punchedInJobs.put(jobCode, true);
    }

    private String getJobDescription (String emplId, int jobCode) {
        List<TimePunchEntry> entries = timePunchDataCache.get(emplId);
        for (TimePunchEntry entry : entries) {
            if (entry.getJob().getJobCode() == jobCode) {
                return entry.getJob().getJobTitle();
            }
        }
        return Integer.toString(jobCode);
    }

    // Punches out and auto-gives 5 more minutes regardless of when punched in.
    @Override
    public void punchOutTimeClock(String emplId, int jobCode, String clientIP) {
        getTimePunchData(emplId);  // For Demo mode and junit tests, make sure the database is initialized
        log.debug("Punching out employee {} for job Code {} from IP {}", emplId, jobCode, clientIP);
        Map<Integer,Boolean> punchedInJobs = punchedInList.get(emplId);
        if ( punchedInJobs.get(jobCode) != null) {
            // Update the worked amount
            List<TimePunchEntry> entries = timePunchDataCache.get(emplId);
            for (TimePunchEntry entry : entries) {
                if (entry.getJob().getJobCode() == jobCode) {
                    // We don't need to track real time; just add an increment
                    entry.setWeekTimeWorked(entry.getWeekTimeWorked() + PUNCH_OUT_INCREMENT);
                    entry.setPayPeriodTimeWorked(entry.getPayPeriodTimeWorked() + PUNCH_OUT_INCREMENT);
                }
            }
            // Zero out the punch-in time
            punchedInJobs.put(jobCode, null);
        } else {
            log.debug("Employee not punched into {} so ignoring punch-out");
        }
    }
}
