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

package edu.wisc.hr.dao.levstmt;

public enum StatementType {
    LEAVE("S", "Leave Statement"),
    REPORT("R", "Leave Report/Furlough"),
    MISSING("M", "Missing Leave Reports");

    private final String key;
    private final String name;

    private StatementType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public static StatementType getStatmentTypeByKey(String key) {
        for (final StatementType statmentType : StatementType.values()) {
            if (statmentType.key.equals(key)) {
                return statmentType;
            }
        }

        throw new IllegalArgumentException("No StatmentType with key: " + key);
    }

    public static StatementType getStatmentTypeByName(String name) {
        for (final StatementType statmentType : StatementType.values()) {
            if (statmentType.name.equals(name)) {
                return statmentType;
            }
        }

        throw new IllegalArgumentException("No StatmentType with name: " + name);
    }
}