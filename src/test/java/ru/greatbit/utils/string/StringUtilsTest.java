package ru.greatbit.utils.string;

import org.junit.Assert;
import org.junit.Test;
import ru.greatbit.utils.beans.SimpleBeanExample;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        assertEquals("Two, beers, or, not, two, beers", StringUtils.listAsString(strings));

        List<SimpleBeanExample> beans = new LinkedList<SimpleBeanExample>();
        beans.add(new SimpleBeanExample(1));
        beans.add(new SimpleBeanExample(2));
        beans.add(null);
        beans.add(new SimpleBeanExample(3));
        assertEquals("1, 2, null, 3", StringUtils.listAsString(beans));
    }

    @Test
    public void testListAsStringNoSpaces() throws Exception {
        List<String> strings = new LinkedList<String>();
        strings.add("Two");
        strings.add("beers");
        strings.add("or");
        strings.add("not");
        strings.add("two");
        strings.add("beers");

        assertEquals("Two,beers,or,not,two,beers", StringUtils.listAsStringNoSpaces(strings));

        List<SimpleBeanExample> beans = new LinkedList<SimpleBeanExample>();
        beans.add(new SimpleBeanExample(1));
        beans.add(new SimpleBeanExample(2));
        beans.add(null);
        beans.add(new SimpleBeanExample(3));
        assertEquals("1,2,null,3", StringUtils.listAsStringNoSpaces(beans));
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

        assertTrue(strings.contains("hide"));
        assertFalse(strings.contains("bride"));
    }

    @Test
    public void addUniqueStringTest(){
        List<String> strings = new LinkedList<String>();
        strings.add("Str1");
        strings.add("Str2");

        strings = StringUtils.addUniqueString("Str1", strings);
        assertThat("Not unique string was added", strings.size(), is(2));
        assertThat("Wrong 1-st string", strings.get(0), is("Str1"));
        assertThat("Wrong 1-st string", strings.get(1), is("Str2"));

        strings = StringUtils.addUniqueString("Str3", strings);
        assertThat("Not unique string was added", strings.size(), is(3));
        assertThat("Wrong 1-st string", strings.get(0), is("Str1"));
        assertThat("Wrong 1-st string", strings.get(1), is("Str2"));
        assertThat("Wrong 1-st string", strings.get(2), is("Str3"));

        strings = StringUtils.addUniqueString(null, strings);
        assertThat("Not unique string was added", strings.size(), is(4));
        assertThat("Wrong 1-st string", strings.get(0), is("Str1"));
        assertThat("Wrong 1-st string", strings.get(1), is("Str2"));
        assertThat("Wrong 1-st string", strings.get(2), is("Str3"));
        assertThat("Wrong 1-st string", strings.get(3), is(""));
    }

    @Test
    public void containsAllTest(){
        String source = "How many javascript developers do we need to replace undefined? - NaN.";
        Assert.assertTrue(StringUtils.containsAll(source, "javascript", "How", "need"));
        Assert.assertFalse(StringUtils.containsAll(source, "javascript", "How", "need", "arrriva!"));
    }

    @Test
    public void containsAnyTest(){
        String source = "How many javascript developers do we need to replace undefined? - NaN.";
        Assert.assertTrue(StringUtils.containsAny(source, "javascript", "How", "need"));
        Assert.assertTrue(StringUtils.containsAny(source, "javascript", "How", "need", "arrriva!"));
        Assert.assertFalse(StringUtils.containsAny(source, "cogito", "ergo", "sum"));
    }

    @Test
    public void removeTailingTest(){
        assertThat("Tailing comma was not removed", StringUtils.removeTailing("Some, tailing, comma,", ","), is("Some, tailing, comma"));
        assertThat("Tailing comma was not removed", StringUtils.removeTailing("Some, tailing, comma, ", ","), is("Some, tailing, comma"));
        assertThat("Tailing slash was not removed", StringUtils.removeTailing("Some, tailing/ slash//", "/"), is("Some, tailing/ slash/"));
        assertThat("Three tailing commas were not removed", StringUtils.removeTailing("Some, tailing,, comma,,,", ",,,"), is("Some, tailing,, comma"));
        assertThat("Tailing slash was unexpectedly removed", StringUtils.removeTailing("Some, tailing/ slash//", ","), is("Some, tailing/ slash//"));
        assertThat(StringUtils.removeTailing("", ","), is(""));
    }

    @Test
    public void removeHeadingTest(){
        assertThat("Heading comma was not removed", StringUtils.removeHeading(",Some, tailing, comma", ","), is("Some, tailing, comma"));
        assertThat("Heading comma was not removed", StringUtils.removeHeading(" ,Some, tailing, comma", ","), is("Some, tailing, comma"));
        assertThat("Heading slash was not removed", StringUtils.removeHeading("/Some, tailing/ slash/", "/"), is("Some, tailing/ slash/"));
        assertThat("Three Heading commas were not removed", StringUtils.removeHeading(",,,Some, tailing,, comma", ",,,"), is("Some, tailing,, comma"));
        assertThat("Heading slash was unexpectedly removed", StringUtils.removeHeading("/Some, tailing/ slash//", ","), is("/Some, tailing/ slash//"));
        assertThat(StringUtils.removeHeading("", ","), is(""));
    }

    @Test
    public void lcsTest(){
        assertThat(StringUtils.lcs("My name is Aziz", "name name is Az"), is(" name is Az"));
        assertThat(StringUtils.lcs("My name is Aziz", "My surname is banana"), is("My name is "));
        assertThat(StringUtils.lcs("My name is Aziz", "My surname is banana Aziz"), is("My name is Aziz"));
        assertThat(StringUtils.lcs("Two beer or not two beer", "To bee or not to bee"), is("To bee or not to bee"));
        assertThat(StringUtils.lcs("My name is Aziz", "If you want to call My nananamename do it loud: call Aziz Namazov"),
                is("My name i Aziz"));
        assertThat(StringUtils.lcs("My name is Aziz", "RockNRoll"), is(""));
    }

    @Test
    public void translitTest(){
        assertThat(StringUtils.translit("Что-то там_такое по-русски. Написано?"), is("CHto-to tam_takoe po-russki. Napisano?"));
    }
}
