package com.appointment.manager.api.repositories.entity;

import com.appointment.manager.api.core.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="case_table", schema = "appointment_manager")
public class CaseEntity {
    @Id
    private Long id;

    @Column
    private String number;

    @ManyToOne
    @JoinColumn(name="patientId")
    private PatientEntity patientEntity;
}
