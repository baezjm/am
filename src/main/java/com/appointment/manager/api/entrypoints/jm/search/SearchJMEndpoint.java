package com.appointment.manager.api.entrypoints.jm.search;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.Paging;
import lombok.Builder;
import lombok.Getter;
import spark.Request;
import spark.Response;

import java.util.List;

public interface SearchJMEndpoint {
    /**
     *
     * @param request
     * @param response
     * @return result of searchJM jm
     */
    SearchJMResponseModel execute(Request request, Response response);

    @Getter
    @Builder
    class SearchJMResponseModel{
        private Paging paging;
        private List<Appointment> results;
    }
}
