package com.appointment.manager.api;

import com.appointment.manager.api.configuration.*;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint;
import com.appointment.manager.api.entrypoints.jmh.search.SearchJMHEndpoint;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import util.java.config.config.Config;
import util.spark.config.Application;

import static spark.Spark.get;

@Slf4j
public class Main extends Application {

    private SearchJMEndpoint searchJMEndpoint;
    private SearchJMHEndpoint searchJMHEndpoint;

    public Main() {
        super();
    }

    public static void main(String[] args) {
        log.info("Run Appointment Manager Application");
        new Main().init();
    }

    @Override
    protected void configure() {
    }

    @Override
    protected void dependencyInjectionConfiguration() {

        Injector injector;
        injector = Guice.createInjector(
                new ConfigurationInjector(),
                new EndpointsInjector(),
                new DataSourceInjector(),
                new RepositoriesInjector(),
                new UseCaseInjector()
            );

        Config.addInjector(APP, injector);
    }

    @Override
    public void addRoutes() {
        Gson gson = getInstance(Gson.class);

        this.searchJMEndpoint = getInstance(SearchJMEndpoint.class);
        this.searchJMHEndpoint = getInstance(SearchJMHEndpoint.class);


        get("/am/search/jm", searchJMEndpoint::execute, gson::toJson);
        get("/am/search/jmh", searchJMHEndpoint::execute, gson::toJson);
    }
}
