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

package org.apereo.portlet.hr.timereporting.service;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apereo.portlet.hr.dao.timereporting.StaffTimePunchDao;
import org.apereo.portlet.hr.model.timereporting.TimePunchEntry;
import org.apereo.portlet.hr.timereporting.util.TimePunchEmployeeKeyGenerator;
import org.apereo.portlet.hr.timereporting.util.TimePunchEmployeeKeyGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Staff Time Punch Service.  The service performs some caching of data.
 *
 * @author James Wennmacher, jwennmacher@unicon.net
 */
@Service
public class StaffTimePunchServiceImpl implements StaffTimePunchService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    StaffTimePunchDao dao;
    TimePunchEmployeeKeyGenerator timePunchEmployeeKeyGenerator = new TimePunchEmployeeKeyGeneratorImpl();

    public void setDao(StaffTimePunchDao dao) {
        this.dao = dao;
    }

    public void setTimePunchEmployeeKeyGenerator(TimePunchEmployeeKeyGenerator timePunchEmployeeKeyGenerator) {
        this.timePunchEmployeeKeyGenerator = timePunchEmployeeKeyGenerator;
    }

    /**
     * Returns a list of <code>TimePunchEntry</code> items for the employee.
     *
     * @param emplId Employee ID
     * @return List of <code>TimePUnchEntry</code> items
     */
    @Override
    @Cacheable(value="timePunch", key = "#emplId", condition = "!#refresh")
    @CachePut(value="timePunch", key = "#emplId", condition = "#refresh")
    public List<TimePunchEntry> getTimePunchEntries(PortletRequest request, String emplId, boolean refresh) {
        log.debug("Invoking dao for time punch entries for employee ID {}, refresh = {}", emplId, refresh);
        return dao.getTimePunchEntries(request, emplId);
    }

    /**
     * Starts logging time for the employee to the indicated job code.
     *
     * @param emplId
     * @param jobCode
     */
    @Override
    @CacheEvict(value = "timePunch", key="#emplId")
    public void punchInTimeClock(PortletRequest request, String emplId, int jobCode, String clientIP) {
        log.debug("Punching in employee ID {}", emplId);
        dao.punchInTimeClock(request, emplId, jobCode, clientIP);
    }

    /**
     * Stops logging time for the employee to the indicated job code.
     *
     * @param emplId
     * @param jobCode
     * @param clientIP
     */
    @Override
    @CacheEvict(value = "timePunch", key="#emplId")
    public void punchOutTimeClock(PortletRequest request, String emplId, int jobCode, String clientIP) {
        log.debug("Punching out employee ID {}", emplId);
        dao.punchOutTimeClock(request, emplId, jobCode, clientIP);
    }
}
