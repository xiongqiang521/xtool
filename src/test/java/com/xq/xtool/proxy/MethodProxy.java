package com.xq.xtool.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //如果传进来的是一个接口（核心)
        } else {
            return run(proxy, method, args);
        }
        return null;
    }

    /**
     * 实现接口的核心方法
     *
     * @param method
     * @param args
     * @return
     */
    public Object run(Object proxy, Method method, Object[] args) {
        System.out.println(proxy);
        Class<?> declaringClass = method.getDeclaringClass();
        System.out.println(method);
        System.out.println(declaringClass);
        System.out.println(Arrays.toString(args));
        //TODO         
        //如远程http调用
        //如远程方法调用（rmi)
        //....
        return "method call success!";
    }

}