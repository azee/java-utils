package ru.greatbit.utils.string;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: azee
 * Date: 1/10/14
 */

public class StringUtilsTest {


    @Test
    public void testEmptyIfNull() throws Exception {
        assertEquals("", StringUtils.emptyIfNull(null));
        assertEquals("", StringUtils.emptyIfNull(""));
        assertEquals("abc", StringUtils.emptyIfNull("abc"));
    }

    @Test
    public void testListAsString() throws Exception {
        List<String> strings = new LinkedList<String>();
        strings.add("Two");
        strings.add("beers");
        strings.add("or");
        strings.add("not");
        strings.add("two");
        strings.add("beers");

        assertEquals("Two,beers,or,not,two,beers", StringUtils.listAsString(strings));
    }

    @Test
    public void testGetMd5String() throws Exception {
        assertEquals("4896cb726e6127ac4e7bb382edae08ab", StringUtils.getMd5String("Super secret string"));
    }

    @Test
    public void testIsStringInList() throws Exception {
        List<String> strings = new LinkedList<String>();
        strings.add("Playing");
        strings.add("hide");
        strings.add("and");
        strings.add("seek");

        assertTrue(StringUtils.isStringInList(strings, "hide"));
        assertFalse(StringUtils.isStringInList(strings, "bride"));

    }
}
