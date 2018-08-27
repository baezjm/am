package com.appointment.manager.api.repositories.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="clinic", schema = "appointment_manager")
public class ClinicEntity {
    @Id
    private Long id;
    @Column
    private String adress;//está mal en la base
}
