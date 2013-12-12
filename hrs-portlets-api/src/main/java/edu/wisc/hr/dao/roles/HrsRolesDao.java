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

package edu.wisc.hr.dao.roles;

import java.util.Set;

/**
 * Gets a set of deep-links into the HRS system
 * 
 * @author Eric Dalquist
 * @version $Revision: 1.1 $
 */
public interface HrsRolesDao {


    /**
     * Get the Set of roles applicable to the user identified by the provided emplId String.
     * Roles recognized by this software are represented in HrsRole.
     *
     * While this returns a Set of Strings rather than of HrsRole s, the meaningful Strings
     * are documented in the HrsRole enum.
     *
     * Implementations may return Strings not represented in the HrsRole enum.  Callers must cope with the Set
     * including Strings not represented in the HrsRole enum.  So handle that IllegalArgumentException on
     * HrsRole.valueOf() if you use it.
     *
     * @param emplId employee identifier identifying the user whose roles are queried
     * @return a potentially empty non-null Set of Strings representing the user's roles
     */
    public Set<String> getHrsRoles(String emplId);
}
