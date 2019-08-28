package com.twuc.wf.twspring.core.schedule.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FunctionTask extends Task implements Runnable {

    private Object object;
    private Class<?> clz;
    private Method method;
    private Object[] args;

    public FunctionTask(TaskType taskType, TaskStatus taskStatus, Object object, Class<?> clz, Method method, Object[] args) {
        super(taskType, taskStatus);
        this.object = object;
        this.clz = clz;
        this.method = method;
        this.args = args;
    }

    @Override
    public void run() {
        try {
            method.invoke(object,args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public Object getObject() {
        return object;
    }

    public Class<?> getClz() {
        return clz;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }
}
