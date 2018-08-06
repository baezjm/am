package com.appointment.manager.api.repositories.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="patient", schema = "appointment_manager")
public class PatientEntity {

    @Id
    private Long id;

    @Column
    private String cuil;
}
