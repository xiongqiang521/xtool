package com.xq.xtool.bean;

import com.xq.xtool.proxy.IUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class RegistBean implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(RegistBean.class);
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("ApplicationContext registed-->{}", applicationContext);
        this.applicationContext = applicationContext;
        BeanFactory parentBeanFactory = applicationContext.getParentBeanFactory();

    }

    public void regist(){

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) this.applicationContext;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(IUserDao.class);

        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
//        beanDefinitionBuilder.addPropertyReference("testService", "testService");

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("testController", beanDefinitionBuilder.getRawBeanDefinition());


        IUserDao userDao = (IUserDao) this.applicationContext.getBean("testController");
        System.out.println(userDao);
        System.out.println(userDao.getUserName());

    }
}
