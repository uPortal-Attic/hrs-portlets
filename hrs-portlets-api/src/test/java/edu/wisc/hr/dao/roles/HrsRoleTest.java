package edu.wisc.hr.dao.roles;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Unit test demonstrating the Java Enum language feature in the context of enum HrsRole.
 *
 * There's nothing interesting about this unit test because there's nothing interesting about the
 * HrsRole enum.  This unit test is intended to demonstrate Java language features for working with the Enum
 * since the intention is that API implementors and API users use the Enum to coordinate role naming.
 */
public class HrsRoleTest {

    /**
     * Demonstrates how one is intended to resolve the role key String at runtime from an HrsRole enum.
     */
    @Test
    public void hrsRolesHaveSensibleToStrings() {

        // you can get the String representation out of an HrsRole.
        assertEquals("ROLE_VIEW_WEB_CLOCK", HrsRole.ROLE_VIEW_WEB_CLOCK.toString());

        // you can get an HrsRole out of a String representation (that, say, came from an HRS system web service)
        assertEquals(HrsRole.ROLE_VIEW_MANAGED_ABSENCES, HrsRole.valueOf("ROLE_VIEW_MANAGED_ABSENCES"));

    }

    /**
     * Demonstrates a heads up to HRS API implementation developers that valueOf() will throw if you
     * invent a role not recognized by the API.
     */
    @Test(expected=IllegalArgumentException.class)
    public void valueOfThrowsWhenNotFound() {

        // of you try to get an HrsRole out of a String representation not recognized by the HrsRole enum
        // then you get to handle an IllegalArgumentException.
        HrsRole.valueOf("NON_EXISTENT_BOGUS_ROLE");

    }

}
