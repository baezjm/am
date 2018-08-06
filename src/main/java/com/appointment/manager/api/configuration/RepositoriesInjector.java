package com.appointment.manager.api.configuration;

import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository;
import com.appointment.manager.api.repositories.impl.DefaultSearchMedicalBoardAppointmentRepository;
import com.google.inject.AbstractModule;

public class RepositoriesInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(SearchMedicalBoardAppointmentRepository.class).to(DefaultSearchMedicalBoardAppointmentRepository.class);
    }
}
