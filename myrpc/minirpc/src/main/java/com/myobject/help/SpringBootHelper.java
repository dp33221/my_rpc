package com.myobject.help;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 9:51 上午
 */
@Component
public class SpringBootHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 获取applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBootHelper.context=applicationContext;
    }
}
