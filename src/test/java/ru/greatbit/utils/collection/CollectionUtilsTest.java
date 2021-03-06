package ru.greatbit.utils.collection;

import javafx.util.Pair;
import org.junit.Test;
import ru.greatbit.utils.beans.BeanWithNamespaceExample;
import ru.greatbit.utils.string.StringUtils;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;
import static ru.greatbit.utils.collection.CollectionUtils.removeDuplicateValues;
import static ru.greatbit.utils.string.StringUtils.listAsString;

/**
 * Created by azee on 4/29/14.
 */
public class CollectionUtilsTest {

    @Test
    public void mergeStringListsTest(){
        List<String> first = new LinkedList<String>();
        List<String> second = new LinkedList<String>();

        first.add("1");
        first.add("2");
        first.add("3");

        second.add("2");
        second.add("3");
        second.add("4");

        List<String> result = CollectionUtils.mergeLists(first, second);
        assertNotNull(result);
        assertThat("Wrong number of items in result list", result.size(), is(4));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("4"));
    }

    @Test
    public void mergeObjectListsTest(){
        List<BeanWithNamespaceExample> first = new LinkedList<BeanWithNamespaceExample>();
        List<BeanWithNamespaceExample> second = new LinkedList<BeanWithNamespaceExample>();

        BeanWithNamespaceExample commonBean = new BeanWithNamespaceExample(3);

        first.add(new BeanWithNamespaceExample(1));
        first.add(new BeanWithNamespaceExample(2));
        first.add(commonBean);

        second.add(commonBean);
        second.add(new BeanWithNamespaceExample(4));
        second.add(new BeanWithNamespaceExample(5));


        List<BeanWithNamespaceExample> result = CollectionUtils.mergeLists(first, second);
        assertNotNull(result);
        assertThat("Wrong number of items in result list", result.size(), is(5));

        List<String> values = new LinkedList<String>();
        for (BeanWithNamespaceExample bean : result){
            values.add(Integer.toString(bean.getValue()));
        }

        assertTrue(values.contains("1"));
        assertTrue(values.contains("2"));
        assertTrue(values.contains("3"));
        assertTrue(values.contains("4"));
        assertTrue(values.contains("5"));
    }

    @Test
    public void mergeObjectListsByValueTest() throws Exception {
        List<BeanWithNamespaceExample> first = new LinkedList<BeanWithNamespaceExample>();
        List<BeanWithNamespaceExample> second = new LinkedList<BeanWithNamespaceExample>();

        first.add(new BeanWithNamespaceExample(1));
        first.add(new BeanWithNamespaceExample(2));
        first.add(new BeanWithNamespaceExample(3));

        second.add(new BeanWithNamespaceExample(3));
        second.add(new BeanWithNamespaceExample(4));
        second.add(new BeanWithNamespaceExample(5));


        List<BeanWithNamespaceExample> result = CollectionUtils.mergeListsByValue(first, second);
        assertNotNull(result);
        assertThat("Wrong number of items in result list", result.size(), is(5));

        List<String> values = new LinkedList<String>();
        for (BeanWithNamespaceExample bean : result){
            values.add(Integer.toString(bean.getValue()));
        }

        assertTrue(values.contains("1"));
        assertTrue(values.contains("2"));
        assertTrue(values.contains("3"));
        assertTrue(values.contains("4"));
        assertTrue(values.contains("5"));
    }

    @Test
    public void listDifferenceStringTest(){
        List<String> first = new LinkedList<String>();
        List<String> second = new LinkedList<String>();

        first.add("1");
        first.add("2");
        first.add("3");

        second.add("1");
        second.add("3");
        second.add("4");

        Difference<String> difference = CollectionUtils.getDiff(first, second);
        assertNotNull(difference);

        assertThat(difference.getAdded().size(), is(1));
        assertNotNull(difference.getAdded().get(0));
        assertThat(difference.getAdded().get(0), is("4"));

        assertThat(difference.getRemoved().size(), is(1));
        assertNotNull(difference.getRemoved().get(0));
        assertThat(difference.getRemoved().get(0), is("2"));

        assertThat(difference.getEqual().size(), is(2));

        difference.getEqual().contains("1");
        difference.getEqual().contains("3");

        assertThat(difference.getMerged().size(), is(4));
        difference.getMerged().contains("1");
        difference.getMerged().contains("2");
        difference.getMerged().contains("3");
        difference.getMerged().contains("4");
    }


    @Test
    public void listDifferenceObjectTest(){
        List<BeanWithNamespaceExample> first = new LinkedList<BeanWithNamespaceExample>();
        List<BeanWithNamespaceExample> second = new LinkedList<BeanWithNamespaceExample>();

        BeanWithNamespaceExample commonBean = new BeanWithNamespaceExample(3);

        first.add(new BeanWithNamespaceExample(1));
        first.add(new BeanWithNamespaceExample(2));
        first.add(commonBean);

        second.add(commonBean);
        second.add(new BeanWithNamespaceExample(4));
        second.add(new BeanWithNamespaceExample(5));


        Difference<BeanWithNamespaceExample> difference = CollectionUtils.getDiff(first, second);
        assertNotNull(difference);

        assertThat(difference.getAdded().size(), is(2));
        assertNotNull(difference.getAdded().get(0));
        assertNotNull(difference.getAdded().get(1));

        assertThat(difference.getRemoved().size(), is(2));
        assertNotNull(difference.getRemoved().get(0));
        assertNotNull(difference.getRemoved().get(1));

        assertThat(difference.getEqual().size(), is(1));
        assertNotNull(difference.getEqual().get(0));
        assertThat(difference.getEqual().get(0), is(commonBean));

        assertThat(difference.getMerged().size(), is(5));
    }

    @Test
    public void listDifferenceAnyObjectTest() throws Exception {
        List<BeanWithNamespaceExample> first = new LinkedList<BeanWithNamespaceExample>();
        List<BeanWithNamespaceExample> second = new LinkedList<BeanWithNamespaceExample>();

        first.add(new BeanWithNamespaceExample(1));
        first.add(new BeanWithNamespaceExample(2));
        first.add(new BeanWithNamespaceExample(3));

        second.add(new BeanWithNamespaceExample(4));
        second.add(new BeanWithNamespaceExample(5));
        second.add(new BeanWithNamespaceExample(3));


        Difference<BeanWithNamespaceExample> difference = CollectionUtils.getDiffAnyObject(first, second);
        assertNotNull(difference);

        assertThat(difference.getAdded().size(), is(2));
        assertNotNull(difference.getAdded().get(0));
        assertNotNull(difference.getAdded().get(1));

        assertThat(difference.getRemoved().size(), is(2));
        assertNotNull(difference.getRemoved().get(0));
        assertNotNull(difference.getRemoved().get(1));

        assertThat(difference.getEqual().size(), is(1));
        assertNotNull(difference.getEqual().get(0));
        assertThat(difference.getEqual().get(0).getValue(), is(3));

        assertThat(difference.getMerged().size(), is(5));
    }

    @Test
    public void removeByIndexTestTest() {
        //LinkedList
        List<String> input = new LinkedList<String>();
        input.add("0");
        input.add("1");
        input.add("2");
        CollectionUtils.removeByIndex(input, 1);
        assertNotNull(input);
        assertThat(input.size(), is(2));
        assertThat(input.get(0), is("0"));
        assertThat(input.get(1), is("2"));
        assertTrue(input instanceof LinkedList);

        //Arrays.ArrayList
        input = Arrays.asList("0", "1", "2");
        input = CollectionUtils.removeByIndex(input, 2);
        assertNotNull(input);
        assertThat(input.size(), is(2));
        assertThat(input.get(0), is("0"));
        assertThat(input.get(1), is("1"));
        assertTrue(input.getClass().getName().equals("java.util.Arrays$ArrayList"));

        //ArrayList
        input = new ArrayList<String>();
        input.add("0");
        input.add("1");
        input.add("2");
        CollectionUtils.removeByIndex(input, 1);
        assertNotNull(input);
        assertThat(input.size(), is(2));
        assertThat(input.get(0), is("0"));
        assertThat(input.get(1), is("2"));
        assertTrue(input instanceof ArrayList);
    }

    @Test
    public void removeDuplicatesFromListTest() {
        List<String> values = Arrays.asList("0", "1", "2", "0", "3", "1");
        values = removeDuplicateValues(values);
        assertNotNull(values);
        assertThat(values.size(), is(4));
        assertTrue(values.contains("0"));
        assertTrue(values.contains("1"));
        assertTrue(values.contains("2"));
        assertTrue(values.contains("3"));
    }

    @Test
    public void removeDuplicatesFromNullListTest() {
        List list = null;
        assertNull(removeDuplicateValues(list));

        List emptyList = removeDuplicateValues(new ArrayList());
        assertNotNull(emptyList);
        assertThat(emptyList.size(), is(0));
    }

    @Test
    public void removeDuplicatesFromListWithEqualsFunctionTest() {
        List<String> values = Arrays.asList("a", "aa", "b", "bb", "bbb", "cccc", "dddd");
        values = removeDuplicateValues(values, (str -> str.length()));
        assertNotNull(values);
        assertThat(values.size(), is(4));
        assertThat(values, containsInAnyOrder("a", "aa", "bbb", "cccc"));

        List<Pair<String, Object>> pairs = new LinkedList<>();
        pairs.add(new Pair<>("1", new Object()));
        pairs.add(new Pair<>("2", new Object()));
        pairs.add(new Pair<>("1", new Object()));
        pairs.add(new Pair<>("3", new Object()));
        pairs.add(new Pair<>("1", new Object()));
        pairs.add(new Pair<>("4", new Object()));
        pairs = removeDuplicateValues(pairs, Pair::getKey);
        assertThat(pairs.size(), is(4));
        assertThat(pairs.stream().map(Pair::getKey).collect(toList()), containsInAnyOrder("1", "2", "3", "4"));
    }

    @Test
    public void removeDuplicatesFromNullMap() {
        Map map = null;
        assertNull(removeDuplicateValues(map));

        Map emptyMap = removeDuplicateValues(new HashMap<String, List<String>>());
        assertNotNull(emptyMap);
        assertThat(emptyMap.size(), is(0));
    }

    @Test
    public void removeDuplicatesFromMap() {
        Map<String, List<String>> values = new HashMap<String, List<String>>();
        values.put("one", Arrays.asList("0", "1", "2", "0", "3", "1"));
        values.put("two", Arrays.asList("0", "1", "2", "1", "2", "0"));

        values = removeDuplicateValues(values);
        assertNotNull(values);
        assertThat(values.size(), is(2));

        List<String> entry = values.get("one");
        assertNotNull(entry);
        assertThat(entry.size(), is(4));
        assertTrue(entry.contains("0"));
        assertTrue(entry.contains("1"));
        assertTrue(entry.contains("2"));
        assertTrue(entry.contains("3"));

        entry = values.get("two");
        assertNotNull(entry);
        assertThat(entry.size(), is(3));
        assertTrue(entry.contains("0"));
        assertTrue(entry.contains("1"));
        assertTrue(entry.contains("2"));
    }

    @Test
    public void reorderTest(){
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> reorder = Arrays.asList(5, 7, 4, 2, 9, 3);

        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("1, 5, 6, 7, 4, 2, 8, 9, 3, 10"));

        reorder = Arrays.asList(10, 1, 6);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("2, 3, 4, 5, 7, 8, 9, 10, 1, 6"));

        reorder = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("1, 2, 3, 4, 5, 6, 7, 8, 9, 10"));

        reorder = new ArrayList<>();
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("1, 2, 3, 4, 5, 6, 7, 8, 9, 10"));

        reorder = Arrays.asList(89, 99, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 16);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("1, 2, 3, 4, 5, 6, 7, 8, 9, 10"));

        reorder = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("10, 9, 8, 7, 6, 5, 4, 3, 2, 1"));

        reorder = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 10);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("9, 8, 7, 6, 5, 4, 3, 2, 1, 10"));

        reorder = Arrays.asList(77, 10, 9, 8, 7, 88, 6, 5, 4, 3, 2, 1, 1, 1, 99, 10, 101);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("9, 8, 7, 6, 5, 4, 3, 2, 1, 10"));

        assertThat(listAsString(CollectionUtils.reorder(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(2, 5, 4))), is("1, 2, 3, 5, 4"));

        assertThat(listAsString(CollectionUtils.reorder(Arrays.asList(3, 5, 2), Arrays.asList(2, 5))), is("3, 2, 5"));

        input = Arrays.asList(3, 4, 1, 2);
        reorder = Arrays.asList(2, 1, 4);
        assertThat(listAsString(CollectionUtils.reorder(input, reorder)), is("3, 2, 1, 4"));
    }

    @Test(timeout=300)
    public void reorderLargeSetTest(){
        final int number = 100000;
        List<Integer> input = new ArrayList<>(number);
        List<Integer> order = new ArrayList<>(number);
        for (int i = 0; i < number; i++){
            input.add(i);
            order.add(number - 1 - i);
        }
        assertThat(listAsString(CollectionUtils.reorder(input, order)), is(listAsString(order)));
    }

    @Test
    public void swapTest(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        CollectionUtils.swap(list, 2, 4);
        assertThat(listAsString(list), is("1, 2, 5, 4, 3"));
    }

    @Test
    public void powerSetTest(){
        Set<Set<String>> sets = CollectionUtils.powerSet(Arrays.asList("a", "b", "c"));
        assertNotNull(sets);
        assertThat(sets.size(), is(8));
        assertThat(sets.stream().map(set -> StringUtils.listAsString(set)).collect(toList()),
                containsInAnyOrder("", "a", "b", "c", "a, b", "a, c", "b, c", "a, b, c"));
    }

    @Test
    public void permutationsTest(){
        List<Set<String>> result = CollectionUtils.permutations(Arrays.asList("1", "2", "3"));
        assertNotNull(result);
        assertThat(result.size(), is(6));
        assertThat(result.stream().map(set -> StringUtils.listAsString(set)).collect(toList()),
                containsInAnyOrder("1, 2, 3", "1, 3, 2", "2, 1, 3", "2, 3, 1", "3, 1, 2", "3, 2, 1"));
    }

}

