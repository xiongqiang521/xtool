package com.xq.xtool.proxy.bean;

import com.xq.xtool.proxy.proxy.IUserDao;
import com.xq.xtool.proxy.proxy.Invoker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SherlockBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        IUserDao invoker = new Invoker().getInstance(IUserDao.class);
        beanFactory.registerSingleton("iUserDao", invoker);
    }
}