package com.twuc.wf.twspring.factory;

import com.twuc.wf.twspring.annotations.Autowried;
import com.twuc.wf.twspring.entity.ClassMethodPair;
import com.twuc.wf.twspring.exceptions.MultipleInjectConstructorException;
import com.twuc.wf.twspring.exceptions.NoSuchBeanDefinitionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.twuc.wf.twspring.constant.CONSTANT.pInfo;

public class BeanFactory {

    public static Set<Class<?>> beansSet = new HashSet<>();
    public static Map<Class<?>, ClassMethodPair> configurationBeanMap = new HashMap<>();
    private static Map<Class<?>, Object> beans = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> clz) throws IllegalAccessException, InstantiationException, NoSuchBeanDefinitionException, MultipleInjectConstructorException, InvocationTargetException {
        if(!beansSet.contains(clz)){
            throw new NoSuchBeanDefinitionException(clz.getName());
        }

        Object object = beans.get(clz);
        if(null==object){
            object = getRecursiveInstance(clz);

            beans.put(clz,object);
            return object;
        }
        return beans.get(clz);
    }

    private static Object getRecursiveInstance(Class<?> clz) throws IllegalAccessException, InstantiationException, MultipleInjectConstructorException, InvocationTargetException {

        Constructor<?>[] constructors = clz.getDeclaredConstructors();
        if(constructors.length>1){
            throw new MultipleInjectConstructorException();
        }else if(constructors.length == 0){
            return clz.newInstance();
        }else { // just a constructor
            for (Constructor<?> constructor : constructors) {
                Autowried autowried = constructor.getDeclaredAnnotation(Autowried.class);
                if (null != autowried) {
                    pInfo(String.format("[ Autowired ] Autowired %s",constructor.toString()));
                    // yep, let's inject this.
                    Class<?>[] types = constructor.getParameterTypes();
                    List<Object> objectList = new ArrayList<>();
                    for (Class<?> type : types) {
                        // constructor
                        objectList.add(getRecursiveInstance(type));
                    }
                    return constructor.newInstance(objectList.toArray());

                }else{
                    pInfo(String.format("[ Autowired ] Class %s do not have autowired constructor",clz));
                    if(findInConfigurationBean(clz)) { // find bean configuration of this cls
                        ClassMethodPair injectMethod = configurationBeanMap.get(clz);
                        return injectMethod.getMethod().invoke(injectMethod.getClz().newInstance());
                    }else{
                        return clz.newInstance();
                    }
                }
            }
        }

        return clz.newInstance();
    }

    private static boolean findInConfigurationBean(Class<?> clz) {
        return configurationBeanMap.keySet().contains(clz);
    }

}
