package com.appointment.manager.api.core.repositories;

import com.appointment.manager.api.core.entities.SearchResponse;

import java.util.Date;

@FunctionalInterface
public interface SearchMedicalBoardAppointmentRepository {

    SearchResponse searchJM(Integer limit, Integer offset, Date dateFrom, Date dateTo);
}
