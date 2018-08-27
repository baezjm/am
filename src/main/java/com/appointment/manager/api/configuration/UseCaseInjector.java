package com.appointment.manager.api.configuration;

import com.appointment.manager.api.core.usecase.jm.search.SearchJM;
import com.appointment.manager.api.core.usecase.jm.search.impl.DefaultSearchJM;
import com.appointment.manager.api.core.usecase.jmh.search.SearchJMH;
import com.appointment.manager.api.core.usecase.jmh.search.impl.DefaultSearchJMH;
import com.google.inject.AbstractModule;

public class UseCaseInjector extends AbstractModule{
    @Override
    protected void configure() {
        bind(SearchJM.class).to(DefaultSearchJM.class);
        bind(SearchJMH.class).to(DefaultSearchJMH.class);
    }
}
