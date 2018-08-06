package com.appointment.manager.api.configuration;

import com.appointment.manager.api.core.usecase.SearchJME;
import com.appointment.manager.api.core.usecase.impl.DefaultSearchME;
import com.google.inject.AbstractModule;

public class UseCaseInjector extends AbstractModule{
    @Override
    protected void configure() {
        bind(SearchJME.class).to(DefaultSearchME.class);
    }
}
