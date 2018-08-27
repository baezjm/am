package com.appointment.manager.api.core.usecase.jm.search.impl;

import com.appointment.manager.api.core.entities.SearchResponse;
import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository;
import com.appointment.manager.api.core.usecase.ValidateUtil;
import com.appointment.manager.api.core.usecase.jm.search.SearchJM;
import lombok.Setter;

import javax.inject.Inject;

public class DefaultSearchJM implements SearchJM {

    @Setter
    private SearchJMModel model;

    @Inject
    private SearchMedicalBoardAppointmentRepository searchAppointmentRepository;

    @Inject
    ValidateUtil validateUtil;

    @Override
    public SearchResponse get() {

        validateModel();

        SearchResponse r = searchAppointmentRepository
                .searchJM(model.getLimit(), model.getOffset(),model.getDateFrom(),model.getDateTo());
        return r;
    }

    private void validateModel() {
        validateUtil.validateNotNull(model,"Model");
    }
}
