package com.ly.demo.common.universal;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author xinre
 */
public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanFactory.applicationContext = applicationContext;
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) {
        return applicationContext.getBean(beanId, clazz);
    }
}
