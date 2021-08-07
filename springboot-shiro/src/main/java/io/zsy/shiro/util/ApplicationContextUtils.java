package io.zsy.shiro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 非工厂管理的类注入bean
 *
 * @author zhangshuaiyin
 * @date 2021-08-07
 **/
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * springboot 启动后创建的工厂就会以参数形式传到这个方法
     *
     * @param applicationContext applicationContext
     * @throws BeansException e
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据bean名字获取工厂中指定bean对象
     *
     * @param beanName bean
     * @return bean 对象
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
}
