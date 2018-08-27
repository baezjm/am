package com.appointment.manager.api.repositories.impl;

import com.appointment.manager.api.core.entities.SearchResponse;
import com.appointment.manager.api.repositories.entity.HomologationAppointmentEntity;
import com.appointment.manager.api.repositories.entity.MedicalBoardAppointmentEntity;
import com.appointment.manager.api.repositories.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultSearchMedicalBoardAppointmentRepositoryTest {

    @InjectMocks
    private DefaultSearchMedicalBoardAppointmentRepository repository;

    @Mock
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_success_jm() {
        Session session = mock(Session.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<MedicalBoardAppointmentEntity> criteriaQuery = mock(CriteriaQuery.class);
        CriteriaQuery<Long> criteriaQueryLong = mock(CriteriaQuery.class);
        Root<MedicalBoardAppointmentEntity> root = mock(Root.class);
        Query<MedicalBoardAppointmentEntity> query = mock(Query.class);
        Query<Long> queryLong = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(eq(MedicalBoardAppointmentEntity.class))).thenReturn(criteriaQuery);
        when(criteriaBuilder.createQuery(eq(Long.class))).thenReturn(criteriaQueryLong);


        when(criteriaQuery.from(eq(MedicalBoardAppointmentEntity.class))).thenReturn(root);
        when(criteriaQueryLong.from(eq(MedicalBoardAppointmentEntity.class))).thenReturn(root);
        when(session.createQuery(eq(criteriaQuery))).thenReturn(query);
        when(session.createQuery(eq(criteriaQueryLong))).thenReturn(queryLong);

        List<MedicalBoardAppointmentEntity> results = new ArrayList<>();

        when(query.getResultList()).thenReturn(results);

        SearchResponse r = repository.searchJM(1, 0, null, null);

        assertThat(r).isNotNull();
    }

    @Test
    public void test_success_jmh() {
        Session session = mock(Session.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<HomologationAppointmentEntity> criteriaQuery = mock(CriteriaQuery.class);
        CriteriaQuery<Long> criteriaQueryLong = mock(CriteriaQuery.class);
        Root<HomologationAppointmentEntity> root = mock(Root.class);
        Query<HomologationAppointmentEntity> query = mock(Query.class);
        Query<Long> queryLong = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(eq(HomologationAppointmentEntity.class))).thenReturn(criteriaQuery);
        when(criteriaBuilder.createQuery(eq(Long.class))).thenReturn(criteriaQueryLong);


        when(criteriaQuery.from(eq(HomologationAppointmentEntity.class))).thenReturn(root);
        when(criteriaQueryLong.from(eq(HomologationAppointmentEntity.class))).thenReturn(root);
        when(session.createQuery(eq(criteriaQuery))).thenReturn(query);
        when(session.createQuery(eq(criteriaQueryLong))).thenReturn(queryLong);

        List<HomologationAppointmentEntity> results = new ArrayList<>();

        when(query.getResultList()).thenReturn(results);

        SearchResponse r = repository.searchJMH(1, 0, null, null);

        assertThat(r).isNotNull();
    }

    @Test
    public void test_error_jm() {
        Session session = mock(Session.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<MedicalBoardAppointmentEntity> criteriaQuery = mock(CriteriaQuery.class);
        CriteriaQuery<Long> criteriaQueryLong = mock(CriteriaQuery.class);
        Root<MedicalBoardAppointmentEntity> root = mock(Root.class);
        Query<MedicalBoardAppointmentEntity> query = mock(Query.class);
        Query<Long> queryLong = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(eq(MedicalBoardAppointmentEntity.class))).thenReturn(criteriaQuery);
        when(criteriaBuilder.createQuery(eq(Long.class))).thenReturn(criteriaQueryLong);


        when(criteriaQuery.from(eq(MedicalBoardAppointmentEntity.class))).thenReturn(root);
        when(criteriaQueryLong.from(eq(MedicalBoardAppointmentEntity.class))).thenReturn(root);
        when(session.createQuery(eq(criteriaQuery))).thenReturn(query);
        when(session.createQuery(eq(criteriaQueryLong))).thenReturn(queryLong);

        when(query.getResultList()).thenThrow(Exception.class);

        assertThatThrownBy(() -> repository.searchJM(1, 0, null, null))
                .isExactlyInstanceOf(RepositoryException.class)
                .hasMessage("Error searching Medical Board Appointments");

    }

    @Test
    public void test_error_jmh() {
        Session session = mock(Session.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<HomologationAppointmentEntity> criteriaQuery = mock(CriteriaQuery.class);
        CriteriaQuery<Long> criteriaQueryLong = mock(CriteriaQuery.class);
        Root<HomologationAppointmentEntity> root = mock(Root.class);
        Query<HomologationAppointmentEntity> query = mock(Query.class);
        Query<Long> queryLong = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(eq(HomologationAppointmentEntity.class))).thenReturn(criteriaQuery);
        when(criteriaBuilder.createQuery(eq(Long.class))).thenReturn(criteriaQueryLong);


        when(criteriaQuery.from(eq(HomologationAppointmentEntity.class))).thenReturn(root);
        when(criteriaQueryLong.from(eq(HomologationAppointmentEntity.class))).thenReturn(root);
        when(session.createQuery(eq(criteriaQuery))).thenReturn(query);
        when(session.createQuery(eq(criteriaQueryLong))).thenReturn(queryLong);

        when(query.getResultList()).thenThrow(Exception.class);

        assertThatThrownBy(() -> repository.searchJMH(1, 0, null, null))
                .isExactlyInstanceOf(RepositoryException.class)
                .hasMessage("Error searching Homologation Appointments");

    }
}