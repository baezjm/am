package com.appointment.manager.api.repositories.entity;

import com.appointment.manager.api.core.entities.Case;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="appointment")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppointmentEntity {
    @Id
    private Long id;

    @Column
    private Integer appointmentType;

    @Column
    private Date appointmentDate;

    @ManyToOne
    @JoinColumn(name="caseId")
    private CaseEntity appointmentCase;

    @Column
    private Date creationDate;
}
