package com.appointment.manager.api.entrypoints.jm.search.impl;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.repositories.SearchAppointmentRepository;
import com.appointment.manager.api.entrypoints.AbstractEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint.SearchJMResponseModel;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;


@Slf4j
public class DefaultSearchJMEndpoint extends AbstractEndpoint<SearchJMResponseModel> implements SearchJMEndpoint {

    @Inject
    private SearchAppointmentRepository searchAppointmentRepository;

    @Override
    protected SearchJMResponseModel doExecute(Request request, Response response) throws Exception {

        log.info("- DefaultSearchJMEndpoint -");

        List<Appointment> r = searchAppointmentRepository.search();


        return SearchJMResponseModel.builder().jmId("JMID").build();
    }
}
