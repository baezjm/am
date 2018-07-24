package com.appointment.manager.api.configuration;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import util.json.JsonUtils;

import javax.inject.Singleton;

/**
 * This class is responsible to inject dependencies of Configuration.
 *
 * @see AbstractModule
 */
public class ConfigurationInjector extends AbstractModule {

    /**
     * Configures the injections
     */
    @Override
    protected void configure() {
    }

    /**
     * Configures the injection of Gson.
     * It disables HTML escaping and serializes/deserializes with lower case and underscores.
     *
     * @return The Gson object
     */
    @Provides
    @Singleton
    public Gson getGson() {
        return JsonUtils.SERIALIZER_GSON;
    }
}