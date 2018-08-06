package com.appointment.manager.api.core.usecase;

import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository.SearchResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;
import java.util.function.Supplier;

public interface SearchJME extends Supplier<SearchResponse>{

    void setModel(SearchJMEModel model);

    default SearchJME initialize(Integer limit, Integer offset, Date dateFrom,Date dateTo){
        SearchJMEModel model = SearchJMEModel.builder()
                .limit(limit)
                .offset(offset)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();
        setModel(model);
        return this;
    }

    @Builder
    @Getter
    class SearchJMEModel{
        private Integer limit;
        private Integer offset;
        private Date dateFrom;
        private Date dateTo;
    }
}
