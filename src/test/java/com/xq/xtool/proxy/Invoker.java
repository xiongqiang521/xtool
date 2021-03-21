package com.xq.xtool.proxy;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建代理
 *
 * @author clonen.cheng
 */
public class Invoker {


    @Bean
    public <T> T getInstance(Class<T> cls) {
        MethodProxy invocationHandler = new MethodProxy();
        Object newProxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                invocationHandler);
        return (T) newProxyInstance;
    }

    public static void test() throws IllegalAccessException, InstantiationException {
        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(IUserDao.class);
        enhancer.setInterfaces(new Class[]{IUserDao.class});

        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return null;
            }
        });
        enhancer.createClass();
//        Class<IUserDao> aClass = enhancer.createClass();
//        Object o = aClass.newInstance();
//        System.out.println(o);
        IUserDao userService = (IUserDao) enhancer.create();
        Class<? extends IUserDao> aClass = userService.getClass();
        System.out.println( "aClass " + aClass);

        IUserDao userDao = aClass.newInstance();
        System.out.println(userDao.getUserName());
        System.out.println(userService.getUserName());
//        System.out.println(userService);
    }

    public static void main(String[] args) {
//        IUserDao invoker = new Invoker().getInstance(IUserDao.class);
//        System.out.println("invoker:" + invoker);
//        System.out.println(invoker.getUserName());
        try {
            test();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}