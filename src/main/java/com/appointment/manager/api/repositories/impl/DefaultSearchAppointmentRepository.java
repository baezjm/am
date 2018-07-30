package com.appointment.manager.api.repositories.impl;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.MedicalBoard;
import com.appointment.manager.api.core.repositories.SearchAppointmentRepository;
import com.appointment.manager.api.repositories.entity.AppointmentEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultSearchAppointmentRepository implements SearchAppointmentRepository {

    @Inject
    @Named("HibernateSessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<Appointment> search() {
        EntityManager session = sessionFactory.createEntityManager();
        try {
            List<AppointmentEntity> appointments = session.createQuery("FROM AppointmentEntity a where a.id = 1").getResultList();

            return appointments.stream()
                    .map(appointmentEntity -> Appointment.builder().id(appointmentEntity.getId()).build())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error", e);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
