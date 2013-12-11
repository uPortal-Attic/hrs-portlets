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

package edu.wisc.hr.dao.roles;

/**
 * Constants representing the roles recognized by the accompanying portlet.
 * Defines keys suitable for use in HrsRoleDao.
 */
public enum HrsRole {

    // TODO: add comments documenting the meaning of each role.

    ROLE_VIEW_ABSENCE_HISTORIES(),

    ROLE_VIEW_MANAGED_ABSENCES(),

    ROLE_VIEW_MANAGED_TIMES(),

    ROLE_VIEW_TIME_SHEET(),

    ROLE_VIEW_TIME_CLOCK(),

    ROLE_VIEW_WEB_CLOCK();

}
