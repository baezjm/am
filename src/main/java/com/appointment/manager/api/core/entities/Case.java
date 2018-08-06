package com.appointment.manager.api.core.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Case {
    private String number;
    private Patient patient;
}
