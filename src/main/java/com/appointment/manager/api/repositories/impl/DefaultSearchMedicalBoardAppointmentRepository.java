package com.appointment.manager.api.repositories.impl;

import com.appointment.manager.api.core.entities.*;
import com.appointment.manager.api.core.repositories.SearchHomologationAppointmentRepository;
import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository;
import com.appointment.manager.api.repositories.entity.AppointmentEntity;
import com.appointment.manager.api.repositories.entity.HomologationAppointmentEntity;
import com.appointment.manager.api.repositories.entity.MedicalBoardAppointmentEntity;
import com.appointment.manager.api.repositories.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class DefaultSearchMedicalBoardAppointmentRepository implements SearchMedicalBoardAppointmentRepository,SearchHomologationAppointmentRepository {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 50;

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public SearchResponse searchJM(Integer limit, Integer offset, Date dateFrom, Date dateTo) {

        Long count = countAppointments(dateFrom,dateTo,MedicalBoardAppointmentEntity.class);

        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MedicalBoardAppointmentEntity> query = builder.createQuery(MedicalBoardAppointmentEntity.class);

            Root<MedicalBoardAppointmentEntity> root = query.from(MedicalBoardAppointmentEntity.class);
            query.select(root);
            if (dateFrom != null && dateTo != null)
                query.where(builder.between(root.get("appointmentDate"), dateFrom, dateTo));

            query.orderBy(builder.desc(root.get("id")));

            Query<MedicalBoardAppointmentEntity> q = session.createQuery(query);

            Integer pOffset = DEFAULT_OFFSET;
            Integer pLimit = DEFAULT_LIMIT;

            if (offset != null)
                pOffset = offset;

            if (limit != null)
                pLimit = limit;

            q.setMaxResults(pLimit);
            q.setFirstResult(pLimit * pOffset);

            List<MedicalBoardAppointmentEntity> result = q.getResultList();

            List<Appointment> appointments = buildAppointments(result);

            return SearchResponse.builder()
                    .paging(Paging.builder()
                            .limit(pLimit)
                            .offset(pOffset)
                            .total(count)
                            .build())
                    .results(appointments)
                    .build();

        } catch (Exception e) {
            throw new RepositoryException("Error searching Medical Board Appointments", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private List<Appointment> buildAppointments(List<? extends AppointmentEntity> result) {
        return result.stream()
                .map(appointmentEntity -> {

                    Patient patient = Patient.builder()
                            .cuil(appointmentEntity.getAppointmentCase().getPatientEntity().getCuil())
                            .firstName(appointmentEntity.getAppointmentCase().getPatientEntity().getFirstName())
                            .surname(appointmentEntity.getAppointmentCase().getPatientEntity().getSurname())
                            .build();

                    Case appointmentCase = Case.builder()
                            .number(appointmentEntity.getAppointmentCase().getNumber())
                            .patient(patient)
                            .build();

                    Clinic clinic = Clinic.builder()
                            .address(appointmentEntity.getClinic().getAdress())
                            .build();

                    return Appointment.builder()
                            .id(appointmentEntity.getId())
                            .appointmentCase(appointmentCase)
                            .appointmentDate(appointmentEntity.getAppointmentDate())
                            .appointmentType(appointmentEntity.getAppointmentType())
                            .creationDate(appointmentEntity.getCreationDate())
                            .clinic(clinic)
                            .build();

                })
                .collect(Collectors.toList());
    }

    private <T> Long countAppointments(Date dateFrom, Date dateTo, Class<T> clazz) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Long> cq = builder.createQuery(Long.class);

            Root<T> root = cq.from(clazz);
            cq.select(builder.count(root));

            if (dateFrom != null && dateTo != null)
                cq.where(builder.between(root.get("appointmentDate"), dateFrom, dateTo));

            Query<Long> q = session.createQuery(cq);
            return q.getSingleResult();
        } catch (Exception e) {
            throw new RepositoryException("Error counting Appointments", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public SearchResponse searchJMH(Integer limit, Integer offset, Date dateFrom, Date dateTo) {
        Long count = countAppointments(dateFrom,dateTo,HomologationAppointmentEntity.class);

        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HomologationAppointmentEntity> query = builder.createQuery(HomologationAppointmentEntity.class);

            Root<HomologationAppointmentEntity> root = query.from(HomologationAppointmentEntity.class);
            query.select(root);
            if (dateFrom != null && dateTo != null)
                query.where(builder.between(root.get("appointmentDate"), dateFrom, dateTo));

            query.orderBy(builder.desc(root.get("id")));

            Query<HomologationAppointmentEntity> q = session.createQuery(query);

            Integer pOffset = DEFAULT_OFFSET;
            Integer pLimit = DEFAULT_LIMIT;

            if (offset != null)
                pOffset = offset;

            if (limit != null)
                pLimit = limit;

            q.setMaxResults(pLimit);
            q.setFirstResult(pLimit * pOffset);

            List<HomologationAppointmentEntity> result = q.getResultList();

            List<Appointment> appointments = buildAppointments(result);

            return SearchResponse.builder()
                    .paging(Paging.builder()
                            .limit(pLimit)
                            .offset(pOffset)
                            .total(count)
                            .build())
                    .results(appointments)
                    .build();

        } catch (Exception e) {
            throw new RepositoryException("Error searching Homologation Appointments", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
