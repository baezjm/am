package com.appointment.manager.api.configuration;

import com.appointment.manager.api.repositories.entity.AppointmentEntity;
import com.appointment.manager.api.repositories.entity.Employer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.schema.Action;
import util.java.config.config.Config;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DataSourceInjector extends AbstractModule {

    /**
     * Provide data source hikari data source.
     *
     * @return the hikari data source
     * @throws Exception the exception
     */
    @Provides
    @Singleton
    private HikariDataSource provideDataSource() {

        Config config = Config.get();
        HikariConfig hikariConfig = new HikariConfig();

        String host = config.getString("datasource.host", "127.0.0.1:3306");
        String db = config.getString("datasource.database", "apointment_manager");
        String password = config.getString("datasource.password", "moCk2$ippO");
        String user = config.getString("datasource.user", "inlex_user");

        log.info(String.format("Configuration DB - Host: %s Db: %s",host,db));


        hikariConfig.setDriverClassName(config.getString("datasource.driver", "com.mysql.jdbc.Driver"));
        hikariConfig.setMaximumPoolSize(config.getInt("datasource.maximumPoolSize", 10));
        hikariConfig.setMinimumIdle(config.getInt("datasource.minimumIdle", 1));
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + "/" + db);
        hikariConfig.setPassword(password);
        hikariConfig.setUsername(user);

        return new HikariDataSource(hikariConfig);

    }

    @Provides
    @Singleton
    @Named("HibernateSessionFactory")
    public SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    @Override
    protected void configure() {

    }
}
