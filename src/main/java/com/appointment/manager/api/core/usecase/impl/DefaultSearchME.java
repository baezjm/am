package com.appointment.manager.api.core.usecase.impl;

import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository;
import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository.SearchResponse;
import com.appointment.manager.api.core.usecase.SearchJME;
import com.appointment.manager.api.core.usecase.ValidateUtil;
import lombok.Setter;

import javax.inject.Inject;

public class DefaultSearchME implements SearchJME{

    @Setter
    private SearchJMEModel model;

    @Inject
    private SearchMedicalBoardAppointmentRepository searchAppointmentRepository;

    @Inject
    ValidateUtil validateUtil;

    @Override
    public SearchResponse get() {

        validateModel();

        SearchResponse r = searchAppointmentRepository
                .search(model.getLimit(), model.getOffset(),model.getDateFrom(),model.getDateTo());
        return r;
    }

    private void validateModel() {
        validateUtil.validateNotNull(model,"Model");
    }
}
