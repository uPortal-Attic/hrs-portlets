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

package edu.wisc.hr.dao.url;

import java.util.HashMap;
import java.util.Map;

/**
 * Static helper implementing HrsUrl.valueOf() enum resolution behavior since HrsUrl.valueOf() does the wrong thing.
 */
public final class HrsUrlHelper {

    /**
     * Private constructor to prevent instantiation of this static helper class.
     */
    private HrsUrlHelper() {
        // do nothing; nothing will ever call this.
    }


    private static Map<String, HrsUrl> CODE_TO_HRS_URLS = new HashMap();

    static {

        for (HrsUrl hrsUrl : HrsUrl.values()) {
            CODE_TO_HRS_URLS.put(hrsUrl.getCode(), hrsUrl);
        }

    }

    /**
     * Converts from a String to the corresponding HrsUrl, with semantics similar to Enum.valueOf(String).
     *
     * Corresponding means the HrsUrl getCode() returns *exactly* the representation.
     *
     * @param representation a code of a known HrsUrl
     * @return the corresponding HrsUrl
     * @throws IllegalArgumentException if the representation is not recognized
     * @throws NullPointerException if the representation is null
     */
    public static HrsUrl valueOf(String representation)
        throws IllegalArgumentException, NullPointerException {

        if (representation == null) {
            throw new NullPointerException("Can't get the HrsUrl value of null.");
        }

        if ( !(CODE_TO_HRS_URLS.containsKey(representation)) ) {
            throw new IllegalArgumentException("[" + representation + "] is not a recognized representation of an " +
                    "HrsUrl");
        }

        return CODE_TO_HRS_URLS.get(representation);

    }
}
