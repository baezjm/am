package com.appointment.manager.api.core.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Appointment {
    private Long id;
    private Date appointmentDate;
    private Case appointmentCase;
    private Integer appointmentType;
    private Date creationDate;
}
