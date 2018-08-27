package com.appointment.manager.api.core.usecase.jmh.search;

import com.appointment.manager.api.core.entities.SearchResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.function.Supplier;

public interface SearchJMH extends Supplier<SearchResponse> {

    void setModel(SearchJMHModel model);

    default SearchJMH initialize(Integer limit, Integer offset, Date dateFrom, Date dateTo){
        SearchJMHModel model = SearchJMHModel.builder()
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
    class SearchJMHModel {
        private Integer limit;
        private Integer offset;
        private Date dateFrom;
        private Date dateTo;
    }
}
