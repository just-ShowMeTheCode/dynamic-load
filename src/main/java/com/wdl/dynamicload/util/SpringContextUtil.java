package com.wdl.dynamicload.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SpringContextUtil</p>
 * <p>Description: ClassDescription</p>
 * <p>Copyright: Copyright (c) 2020</p>
 *
 * @author wangdali
 * @version 1.0
 * @date 2021/1/21 9:27
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    public static void registerBean(Class ... classes) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        //获取BeanFactory
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) factory.getBean(RequestMappingHandlerMapping.class);
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        for (Class clazz : classes) {
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            if (factory.containsBeanDefinition(getBeanNameByClass(clazz.getName()))) {
                throw new RuntimeException("DynamicLoad Exception："+ clazz.getName() +" bean repeat load");
            }
            beanDefinitions.add(beanDefinition);
        }
        beanDefinitions.forEach(beanDefinition -> {
            factory.registerBeanDefinition(getBeanNameByClass(beanDefinition.getBeanClassName()), beanDefinition);
        });
        for (Class clazz : classes) {
            if (clazz.getAnnotation(RestController.class) != null) {
                try {
                    Method method= requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods",Object.class);
                    method.setAccessible(true);
                    method.invoke(requestMappingHandlerMapping,getBeanNameByClass(clazz.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeBean(Class ... classes) {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) SpringContextUtil.getApplicationContext();
        for (Class clazz : classes) {
            factory.removeBeanDefinition(getBeanNameByClass(clazz.getName()));
        }
    }

    public static String getBeanNameByClass(String className) {
        return StringUtils.uncapitalize(StringUtils.substringAfterLast(className, "."));
    }

    public static boolean isSpringBeanClass(Class clazz) {
        if (clazz != null && (clazz.getAnnotation(Controller.class) != null || clazz.getAnnotation(RestController.class) != null
                || clazz.getAnnotation(Service.class) != null || clazz.getAnnotation(Mapper.class) != null)) {
            return true;
        }
        return false;
    }

}
