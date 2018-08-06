package com.appointment.manager.api.core.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Paging {
    private Long total;
    private Integer offset;
    private Integer limit;
}
