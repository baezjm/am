package com.appointment.manager.api.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.java.config.config.Config;

import javax.inject.Named;
import javax.inject.Singleton;

@Slf4j
public class DataSourceInjector extends AbstractModule {

    @Provides
    @Singleton
    public SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    @Override
    protected void configure() {

    }
}
