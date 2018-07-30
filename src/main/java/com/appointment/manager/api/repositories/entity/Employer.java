package com.appointment.manager.api.repositories.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Employer {

    private Long id;
    private String firstName;
    private String surname;
    private String cuit;
}
