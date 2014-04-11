package com.azee.utils.beans;

import javax.xml.bind.annotation.*;

/**
 * Created by azee on 4/10/14.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BeanWithoutNamespaceExample", propOrder = {
        "value"
})
@XmlRootElement(name = "beanExample", namespace = "beans.utils.azee.com")
public class BeanWithNamespaceExample {
    @XmlElement(required = true,  namespace = "beans.utils.azee.com")
    protected int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
