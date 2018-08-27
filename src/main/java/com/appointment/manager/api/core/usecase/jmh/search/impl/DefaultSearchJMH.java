package com.appointment.manager.api.core.usecase.jmh.search.impl;

import com.appointment.manager.api.core.entities.SearchResponse;
import com.appointment.manager.api.core.repositories.SearchHomologationAppointmentRepository;
import com.appointment.manager.api.core.usecase.ValidateUtil;
import com.appointment.manager.api.core.usecase.jmh.search.SearchJMH;
import lombok.Setter;

import javax.inject.Inject;

public class DefaultSearchJMH implements SearchJMH{

    @Setter
    private SearchJMHModel model;

    @Inject
    private SearchHomologationAppointmentRepository searchAppointmentRepository;

    @Inject
    ValidateUtil validateUtil;

    @Override
    public SearchResponse get() {
        validateModel();

        SearchResponse r = searchAppointmentRepository
                .searchJMH(model.getLimit(), model.getOffset(),model.getDateFrom(),model.getDateTo());
        return r;
    }

    private void validateModel() {
        validateUtil.validateNotNull(model,"Model");
    }
}
