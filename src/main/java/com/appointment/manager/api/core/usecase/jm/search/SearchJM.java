package com.appointment.manager.api.core.usecase.jm.search;

import com.appointment.manager.api.core.entities.SearchResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.function.Supplier;

public interface SearchJM extends Supplier<SearchResponse>{

    void setModel(SearchJMModel model);

    default SearchJM initialize(Integer limit, Integer offset, Date dateFrom, Date dateTo){
        SearchJMModel model = SearchJMModel.builder()
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
    class SearchJMModel {
        private Integer limit;
        private Integer offset;
        private Date dateFrom;
        private Date dateTo;
    }
}
