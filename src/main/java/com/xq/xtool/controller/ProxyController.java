

package com.xq.xtool.controller;

import com.xq.xtool.proxy.bean.RegistBean;
import com.xq.xtool.proxy.proxy.IUserDao;
import com.xq.xtool.proxy.proxy.Invoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

    @Autowired(required = false)
    private IUserDao userDao;

    @Autowired
    private RegistBean registBean;

    @GetMapping("/proxy")
    public String test(){
        IUserDao userDao = new Invoker().getInstance(IUserDao.class);
        System.out.println(userDao);
        registBean.regist("iUserDao", userDao.getClass());

//        Object iUserDao = registBean.getBean("iUserDao");
//        System.out.println(iUserDao);

        IUserDao bean = registBean.getBean(IUserDao.class);
        System.out.println(bean);

        return bean.getUserName();
    }
}
