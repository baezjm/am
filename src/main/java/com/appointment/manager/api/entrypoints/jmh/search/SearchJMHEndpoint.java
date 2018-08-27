package com.appointment.manager.api.entrypoints.jmh.search;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.Paging;
import lombok.Builder;
import lombok.Getter;
import spark.Request;
import spark.Response;

import java.util.List;

public interface SearchJMHEndpoint {
    /**
     *
     * @param request
     * @param response
     * @return result of searchJM jm
     */
    SearchJMHResponseModel execute(Request request, Response response);

    @Getter
    @Builder
    class SearchJMHResponseModel {
        private Paging paging;
        private List<Appointment> results;
    }
}
