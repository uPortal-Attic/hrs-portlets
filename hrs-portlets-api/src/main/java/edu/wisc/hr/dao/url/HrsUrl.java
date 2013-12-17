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
 * Constants representing the keys of URLs recognized by the accompanying portlet.
 * Defines keys suitable for use in HrsUrlDao.
 *
 * WARNING: This enum does not work the way you might expect in that HrsUrl.valueOf(String) is broken.
 * Use HrsUrl.fromString(String) instead.  See also accompanying unit test.
 */
public enum HrsUrl {

    // TODO: add comments documenting the intended meaning/usage of each URL key

    APPROVE_ABSENCE("Approve Absence"),

    APPROVE_PAYABLE_TIME("Approve Payable time"),

    /**
     * URL to benefits enrollment.
     * Presence of this key in the Map causes the portlet to present a link inviting user to a benefit enrollment
     * opportunity (so only include if there actually might be such an opportunity).
     */
    BENEFITS_ENROLLMENT("Benefits Enrollment"),

    BENEFITS_SUMMARY("Benefits Summary"),

    DEPENDENT_COVERAGE("Dependent Coverage"),

    DEPENDENT_INFORMATION("Dependent Information"),

    OPEN_ENROLLMENT_HIRE_EVENT("Open Enrollment/Hire Event"),

    PERSONAL_INFORMATION("Personal Information"),

    REQUEST_ABSENCE("Request Absence"),

    TIME_MANAGEMENT("Time Management"),

    TIMESHEET("Timesheet"),

    UPDATE_TSA_DEDUCTIONS("Update TSA Deductions"),

    PAYABLE_TIME_DETAIL("Payable time detail"),

    TIME_EXCEPTIONS("Time Exceptions"),

    WEB_CLOCK("Web Clock");

    private static Map<String, HrsUrl> CODE_TO_HRS_URLS = new HashMap();

    static {

        for (HrsUrl hrsUrl : HrsUrl.values()) {
            CODE_TO_HRS_URLS.put(hrsUrl.getCode(), hrsUrl);
        }

    }

    /**
     * In practice there are "magic String" URL keys that HrsUrlDao implementations use to communicate HRS URLs
     * from DAO implementation to relying portlet.  These magic Strings have been harvested from the portlet
     * implementation code.  Alas they do not match the names of the enum instances.
     */
    private String code;

    private HrsUrl(String s) {
        this.code = s;
    }

    /**
     * Get the String representation of the HrsUrl enum instance, which is *not* reliably the literal name of the enum.
     * (As of this writing the enum name and the code never match, but this API definition is not intended to specify
     * that the names and codes must not match.)
     * @return the String key actually used to represent the URL key.
     */
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
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
    public static HrsUrl fromString(String representation)
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
