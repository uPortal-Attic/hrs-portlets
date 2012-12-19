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
package edu.wisc.hr.dm.person;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 * Adds utility {@link #getJobMap()} method to {@link PersonInformation}
 * 
 * @author Eric Dalquist
 */
public abstract class AbstractPersonInformation {
    public abstract List<Job> getJobs();
    
    public final Map<Integer, Job> getJobMap() {
        final List<Job> jobs = getJobs();
        return Maps.uniqueIndex(jobs, new Function<Job, Integer>() {
            public Integer apply(Job input) {
                return input.getId();
            }
        });
    }
}
