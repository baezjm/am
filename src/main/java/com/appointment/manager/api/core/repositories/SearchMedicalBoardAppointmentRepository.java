package com.appointment.manager.api.core.repositories;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.Paging;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@FunctionalInterface
public interface SearchMedicalBoardAppointmentRepository {

    SearchResponse search(Integer limit, Integer offset, Date dateFrom, Date dateTo);

    @Builder
    @Getter
    class SearchResponse{
        private Paging paging;
        private List<Appointment> results;
    }
}
