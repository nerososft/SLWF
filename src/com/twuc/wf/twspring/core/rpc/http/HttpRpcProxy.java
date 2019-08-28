package com.twuc.wf.twspring.core.rpc.http;

import com.twuc.wf.demoWebProject.entity.User;

import java.lang.reflect.Proxy;

public class HttpRpcProxy<T> {
    public static <T> T getProxy(final Class<?> interfaces){
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(),new Class<?>[]{interfaces}, (proxy, method, args) -> {
            // todo remote http request

            return new User("aa",1);
        });
    }
}
