package com.appointment.manager.api.repositories.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="homologationAppointment")
public class HomologationAppointmentEntity extends AppointmentEntity{
    private Long id;
}
