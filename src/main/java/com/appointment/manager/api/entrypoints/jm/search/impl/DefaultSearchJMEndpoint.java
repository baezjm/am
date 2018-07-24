package com.appointment.manager.api.entrypoints.jm.search.impl;

import com.appointment.manager.api.entrypoints.AbstractEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint.SearchJMResponseModel;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

@Slf4j
public class DefaultSearchJMEndpoint extends AbstractEndpoint<SearchJMResponseModel> implements SearchJMEndpoint {

    @Override
    protected SearchJMResponseModel doExecute(Request request, Response response) throws Exception {

        log.info("- DefaultSearchJMEndpoint -");

        return SearchJMResponseModel.builder().jmId("JMID").build();
    }
}
