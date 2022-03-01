package com.wdl.dynamicload.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author fumj
 * @version 1.0
 * @date 2022/2/17 19:14
 */
@Configuration
public class GroovyConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public HikariDataSourceCreator getDsCreator() {
        return new HikariDataSourceCreator();
    }
}
