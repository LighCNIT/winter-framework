package org.winterframework.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 一个简易的bean工厂
 */
public class BeanFactory {

    private Map<String,Object> beanMap = new HashMap<>();

    public void registerBean(String name,Object bean){
        beanMap.put(name,bean);
    }

    public Object getBean(String name){
        return beanMap.get(name);
    }
}