package edu.wisc.hr.dao.url;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases demonstrating that HrsUrlHelper.valueOf() has same semantics as HrsUrl.valueOf() should have had were
 * HrsUrl keys proper Enum-style values.
 */
public class HrsUrlHelperTest {

    /**
     * Test that HrsUrl.valueOf() successfully resolves HrsUrls from their String code representations.
     */
    @Test
    public void testResolvesHrsUrl(){

        assertEquals(HrsUrl.OPEN_ENROLLMENT_HIRE_EVENT, HrsUrlHelper.valueOf("Open Enrollment/Hire Event"));

        assertEquals(HrsUrl.REQUEST_ABSENCE, HrsUrlHelper.valueOf("Request Absence"));

    }

    /**
     * Test that HrsUrl.valueOf() throws NPE when passed null.
     */
    @Test(expected = NullPointerException.class)
    public void testThrowsNullPointerExceptionOnNullArgument() {
        HrsUrlHelper.valueOf(null);
    }

    /**
     * Test that HrsUrlHelper.valueOf() throws IllegalArgumentException when asked to resolve a String it does not
     * recognize.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsIllegalArgumentExceptionOnUnrecognizedKey() {
        HrsUrlHelper.valueOf("BogusNonExistentUrlIdentifier");
    }
}
