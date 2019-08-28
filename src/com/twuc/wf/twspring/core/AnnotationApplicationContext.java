package com.twuc.wf.twspring.core;

import com.twuc.wf.twspring.annotations.*;
import com.twuc.wf.twspring.core.schedule.task.ScheduleIntervalTask;
import com.twuc.wf.twspring.core.schedule.task.ScheduleTask;
import com.twuc.wf.twspring.core.schedule.task.ScheduleTimeoutTask;
import com.twuc.wf.twspring.entity.ClassMethodPair;
import com.twuc.wf.twspring.exceptions.MultipleInjectConstructorException;
import com.twuc.wf.twspring.exceptions.NoSuchBeanDefinitionException;
import com.twuc.wf.twspring.factory.BeanFactory;
import com.twuc.wf.twspring.simplehttpserver.contract.RequestMethod;
import com.twuc.wf.twspring.utils.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.twuc.wf.twspring.constant.CONSTANT.pInfo;

public class AnnotationApplicationContext {

    private Class<?> context;

    public static Map<String, ClassMethodPair> getMappingMap = new HashMap<>();
    public static Map<String, ClassMethodPair>  postMappingMap = new HashMap<>();
    public static Map<Class<?>, Class<?>>  exceptionHandlerMap = new HashMap<>();

    public static Set<ScheduleTask> scheduleTaskSet = new HashSet<>();

    AnnotationApplicationContext(Class<?> context) throws IllegalAccessException, MultipleInjectConstructorException, NoSuchBeanDefinitionException, InstantiationException, InvocationTargetException {
        this.context = context;
        this.scanAnnotations();
    }

    private void scanAnnotations() throws InvocationTargetException, NoSuchBeanDefinitionException, MultipleInjectConstructorException, InstantiationException, IllegalAccessException {


        List<Class<?>> classes = ClassUtil.getAllClassByPackageName(this.context.getPackage());

        for(Class<?> c:classes){

            // scan @Component
            Component component = c.getDeclaredAnnotation(Component.class);
            if(null != component) {
                BeanFactory.beansSet.add(c);
                Method[] methods = c.getMethods();
                for(Method m:methods){
                    Scheduled scheduled = m.getDeclaredAnnotation(Scheduled.class);
                    if(null != scheduled){
                        if(scheduled.fixedRate()!=0) {
                            scheduleTaskSet.add(new ScheduleIntervalTask(BeanFactory.getBean(c), c, m, null, (long) scheduled.fixedRate()));
                        }else if(scheduled.fixedRate()==0){
                            scheduleTaskSet.add(new ScheduleTimeoutTask(BeanFactory.getBean(c), c, m, null, (long) scheduled.initialDelay()));
                        }
                    }
                }
            }

            // scan @RestController
            RestController restController = c.getDeclaredAnnotation(RestController.class);
            if(null != restController){
                BeanFactory.beansSet.add(c);

                Method[] methods = c.getMethods();
                for(Method m:methods){
                    RequestMapping requestMapping = m.getDeclaredAnnotation(RequestMapping.class);
                    if(null != requestMapping){
                        if (requestMapping.method() == RequestMethod.POST) {
                            postMappingMap.put(restController.value() + requestMapping.value(), new ClassMethodPair(c, m));
                        } else {
                            getMappingMap.put((restController.value() + requestMapping.value()).split("\\?")[0], new ClassMethodPair(c, m));
                        }
                    }
                }
            }

            // scan @ControllerAdvice
            ControllerAdvice controllerAdvice = c.getDeclaredAnnotation(ControllerAdvice.class);
            if(null != controllerAdvice){
                BeanFactory.beansSet.add(c);

                for(Class<?> packageClz:controllerAdvice.basePackagesClasses()) {
                    exceptionHandlerMap.put(packageClz, c);
                }
            }

            // scan @Configuration and @Bean to configurationBeanMap
            Configuration configuration = c.getDeclaredAnnotation(Configuration.class);
            if(null != configuration) {
                for (Method method : c.getMethods()){
                    Bean bean = method.getDeclaredAnnotation(Bean.class);
                    if(null != bean){
                        Class<?> configBeanType = method.getReturnType();
                        BeanFactory.configurationBeanMap.put(configBeanType,new ClassMethodPair(c,method));
                    }
                }
            }

        }

        pInfo("[ "+Thread.currentThread().getName()+" ] " + BeanFactory.configurationBeanMap.toString());
        pInfo("[ "+Thread.currentThread().getName()+" ] " + getMappingMap.toString());
        pInfo("[ "+Thread.currentThread().getName()+" ] " + postMappingMap.toString());
        pInfo("[ "+Thread.currentThread().getName()+" ] " + exceptionHandlerMap.toString());
    }


    public Object getBean(Class<?> clz) throws IllegalAccessException, NoSuchBeanDefinitionException, InstantiationException, MultipleInjectConstructorException, InvocationTargetException {
        return BeanFactory.getBean(clz);
    }
}
