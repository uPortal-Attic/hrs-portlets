package edu.wisc.hr.dao.url;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests documenting the getCode() feature of HrsUrl and that HrsUrl.valueOf() is not what one would want it to
 * be.  See also HrsUrlHelper for usable valueOf(String) behavior.
 */
public class HrsUrlTest {

    /**
     * Test that the String representation of an HrsUrl is its String representation "code" as used by the known
     * HrsUrlDao implementations rather than just the literal name of the enum instance.
     */
    @Test
    public void testHrsUrlsToStringToTheirCodes() {

        assertEquals("Approve Payable time", HrsUrl.APPROVE_PAYABLE_TIME.toString());

    }

    @Test
    public void testHrsUrlsGetCode() {
        assertEquals("Benefits Summary", HrsUrl.BENEFITS_SUMMARY.getCode());
    }

    /**
     * Heads up, this is important.  HrsUrl.valueOf() doesn't work the way one expects in enums because the
     * pre-established Url key values are goofy Strings that aren't usable as names of HrsUrl enum instances.
     *
     */
    @Test(expected=IllegalArgumentException.class)
    public void testValueOf() {
        HrsUrl.valueOf("Open Enrollment/Hire Event");
    }

}
