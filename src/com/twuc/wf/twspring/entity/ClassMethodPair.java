package com.twuc.wf.twspring.entity;

import java.lang.reflect.Method;

public class ClassMethodPair {
    private Class<?> clz;
    private Method method;



    public ClassMethodPair(Class<?> clz, Method method) {
        this.clz = clz;
        this.method = method;
    }


    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "ClassMethodPair{" +
                "clz=" + clz +
                ", method=" + method +
                '}';
    }
}
