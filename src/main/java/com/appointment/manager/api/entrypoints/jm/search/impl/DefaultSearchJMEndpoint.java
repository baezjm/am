package com.appointment.manager.api.entrypoints.jm.search.impl;

import com.appointment.manager.api.core.entities.SearchResponse;
import com.appointment.manager.api.core.usecase.ValidateUtil;
import com.appointment.manager.api.core.usecase.jm.search.SearchJM;
import com.appointment.manager.api.entrypoints.AbstractEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint.SearchJMResponseModel;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;
import util.spark.config.Application;

import javax.inject.Inject;
import java.util.Date;


@Slf4j
public class DefaultSearchJMEndpoint extends AbstractEndpoint<SearchJMResponseModel> implements SearchJMEndpoint {

    @Inject
    private ValidateUtil validateUtil;

    @Override
    protected SearchJMResponseModel doExecute(Request request, Response response) {

        Integer limit = getQueryParamAsInteger(request,"limit");
        Integer offset = getQueryParamAsInteger(request,"offset");
        Date dateFrom = getQueryParamAsDate(request,"date.from");
        Date dateTo = getQueryParamAsDate(request,"date.to");

        validate(limit,offset);

        log.info("DefaultSearchJMEndpoint -" + " limit " + limit + " offset " + offset + " dateFrom " + dateFrom + " dateTo " + dateTo);

        SearchResponse r = Application.getInstance(SearchJM.class).initialize(limit, offset,dateFrom,dateTo).get();

        return SearchJMResponseModel.builder().paging(r.getPaging()).results(r.getResults()).build();
    }

    private void validate(Integer limit, Integer offset) {
        validateUtil.validateNotNull(limit,"limit");
        validateUtil.validatePositive(limit,"limit");
        validateUtil.validateNotNull(offset,"offset");
        validateUtil.validatePositive(offset,"offset");
        validateUtil.validateLowerThanOrEqualsLimit((long)limit,100L, "limit");

    }
}
