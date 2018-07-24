package com.appointment.manager.api.configuration;

import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.impl.DefaultSearchJMEndpoint;
import com.google.inject.AbstractModule;

public class EndpointsInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(SearchJMEndpoint.class).to(DefaultSearchJMEndpoint.class);
    }
}
