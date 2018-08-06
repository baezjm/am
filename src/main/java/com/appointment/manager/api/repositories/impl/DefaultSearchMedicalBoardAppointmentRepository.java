package com.appointment.manager.api.repositories.impl;

import com.appointment.manager.api.core.entities.Appointment;
import com.appointment.manager.api.core.entities.Case;
import com.appointment.manager.api.core.entities.Paging;
import com.appointment.manager.api.core.entities.Patient;
import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository;
import com.appointment.manager.api.repositories.entity.MedicalBoardAppointmentEntity;
import com.appointment.manager.api.repositories.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class DefaultSearchMedicalBoardAppointmentRepository implements SearchMedicalBoardAppointmentRepository {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 50;


    @Inject
    private SessionFactory sessionFactory;

    @Override
    public SearchResponse search(Integer limit, Integer offset, Date dateFrom, Date dateTo) {

        Long count = getCount(dateFrom,dateTo);

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

            List<Appointment> appointments = result.stream()
                    .map(appointmentEntity -> {

                        Patient patient = Patient.builder()
                                .cuil(appointmentEntity.getAppointmentCase().getPatientEntity().getCuil())
                                .build();

                        Case appointmentCase = Case.builder()
                                .number(appointmentEntity.getAppointmentCase().getNumber())
                                .patient(patient)
                                .build();

                        return Appointment.builder()
                                .id(appointmentEntity.getId())
                                .appointmentCase(appointmentCase)
                                .appointmentDate(appointmentEntity.getAppointmentDate())
                                .appointmentType(appointmentEntity.getAppointmentType())
                                .creationDate(appointmentEntity.getCreationDate())
                                .build();

                    })
                    .collect(Collectors.toList());

            return SearchResponse.builder()
                    .paging(Paging.builder()
                            .limit(pLimit)
                            .offset(pOffset)
                            .total(count)
                            .build())
                    .results(appointments)
                    .build();

        } catch (Exception e) {
            throw new RepositoryException("Error searching Appointments", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private Long getCount(Date dateFrom, Date dateTo) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Long> cq = builder.createQuery(Long.class);

            Root<MedicalBoardAppointmentEntity> root = cq.from(MedicalBoardAppointmentEntity.class);
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
}
