package com.appointment.manager.api.core.repositories;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.MedicalBoard;

import java.util.List;

@FunctionalInterface
public interface SearchAppointmentRepository {

    List<Appointment> search();
}
