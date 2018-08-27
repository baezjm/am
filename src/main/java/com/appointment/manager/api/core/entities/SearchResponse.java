package com.appointment.manager.api.core.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchResponse {
    private Paging paging;
    private List<Appointment> results;
}
