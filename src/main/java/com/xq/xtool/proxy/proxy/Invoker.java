package com.xq.xtool.proxy.proxy;


import java.lang.reflect.Proxy;

/**
 * 创建代理
 *
 * @author clonen.cheng
 */
public class Invoker {


    public <T> T getInstance(Class<T> cls) {
        MethodProxy invocationHandler = new MethodProxy();
        Object newProxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                invocationHandler);
        return (T) newProxyInstance;
    }

    public static void main(String[] args) {
        IUserDao invoker = new Invoker().getInstance(IUserDao.class);
        System.out.println("invoker:" + invoker);
        System.out.println(invoker.getUserName());
    }
}