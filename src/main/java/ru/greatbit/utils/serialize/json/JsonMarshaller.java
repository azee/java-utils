package ru.greatbit.utils.serialize.json;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.springframework.stereotype.Service;

/**
 * Created by azee on 4/11/14.
 */
@Service
public class JsonMarshaller {


    /**
     * Marshall an object without root
     * @param object - an object to marshall
     * @param <T> - object class
     * @return String - marshalled object
     * @throws Exception
     */
    public <T> String marshal(T object) throws Exception{
        return marshal(object, "");
    }


    /**
     * Marshall an object
     * Object can be wrapped into root element if provided
     * @param object - an object to marshall
     * @param rootName - String root name to wrap
     * @param <T> - object class
     * @return - String - marshalled object
     * @throws Exception
     */
    public <T> String marshal(T object, String rootName) throws Exception{
        String result;
        ObjectMapper mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        mapper.getSerializationConfig().setAnnotationIntrospector(introspector);
        result = mapper.writeValueAsString(object);
        if (rootName != null && !"".equals(rootName)){
            result = "{\"" + rootName + "\": " + result + "}";
        }
        return result;
    }
}
