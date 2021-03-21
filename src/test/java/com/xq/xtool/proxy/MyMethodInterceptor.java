package com.xq.xtool.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] arg, org.springframework.cglib.proxy.MethodProxy proxy) throws Throwable {
        System.out.println("Before:" + method);
        System.out.println(proxy.toString());
//        Object object = proxy.invokeSuper(obj, arg);
        System.out.println("After:" + method);
        return "success";
    }
}