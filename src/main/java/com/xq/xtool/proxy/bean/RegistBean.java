package com.xq.xtool.proxy.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RegistBean implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(RegistBean.class);
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("ApplicationContext registed-->{}", applicationContext);
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }

    public Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public <T> void regist(String name, Class<T> clazz){
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) this.applicationContext;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
//        beanDefinitionBuilder.addPropertyReference("testService", "testService");

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition(name, beanDefinitionBuilder.getRawBeanDefinition());


//        IUserDao userDao = (IUserDao) this.applicationContext.getBean("IUserDao");
//        System.out.println(userDao);
//        System.out.println(userDao.getUserName());

    }
}
