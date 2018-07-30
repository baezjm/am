package com.appointment.manager.api.configuration;

import com.appointment.manager.api.core.repositories.SearchAppointmentRepository;
import com.appointment.manager.api.repositories.impl.DefaultSearchAppointmentRepository;
import com.google.inject.AbstractModule;

public class RepositoriesInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(SearchAppointmentRepository.class).to(DefaultSearchAppointmentRepository.class);
    }
}
