package ru.greatbit.utils.beans;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.Is.is;

/**
 * Created by azee on 4/11/14.
 */
public class CopyBeanUtilsTest {

    @Test
    public void testGetCopy() throws Exception {
        BeanWithoutNamespaceExample bean = new BeanWithoutNamespaceExample();
        bean.setValue(1);

        BeanWithoutNamespaceExample copy = CopyBeanUtils.getCopy(bean);
        assertNotNull(copy);
        Assert.assertThat("Wrong value of the copy", copy.getValue(), is(1));
        Assert.assertFalse(bean == copy);
    }
}
