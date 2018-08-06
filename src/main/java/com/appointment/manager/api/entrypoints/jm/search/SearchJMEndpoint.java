package com.appointment.manager.api.entrypoints.jm.search;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.Paging;
import lombok.Builder;
import lombok.Getter;
import spark.Request;
import spark.Response;

import java.io.Serializable;
import java.util.List;

public interface SearchJMEndpoint {
    /**
     *
     * @param request
     * @param response
     * @return result of search jm
     */
    SearchJMResponseModel execute(Request request, Response response);

    @Getter
    @Builder
    class SearchJMRequestModel implements Serializable {
        private String jmId;
    }

    @Getter
    @Builder
    class SearchJMResponseModel implements Serializable {
        private Paging paging;
        private List<Appointment> results;
    }
}
