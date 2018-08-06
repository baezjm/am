package com.appointment.manager.api.helper;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import util.java.config.config.Config;

/**
 * Created by ppdcruz on 27/02/18.
 */
public class TestApplicationHelper {

    private static final String APP = "app";

    public static void setInjector(AbstractModule... module) {

        Config.addInjector(APP, Guice.createInjector(module));
    }

    public static void clearInjector() {

        Config.addInjector(APP, Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

            }
        }));
    }

}
