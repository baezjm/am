package com.appointment.manager.api.core.usecase.impl;

import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository;
import com.appointment.manager.api.core.repositories.SearchMedicalBoardAppointmentRepository.SearchResponse;
import com.appointment.manager.api.core.usecase.ValidateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultSearchMETest {

    @InjectMocks
    private DefaultSearchME defaultSearchME;

    @Spy
    private ValidateUtil validateUtil;

    @Mock
    private SearchMedicalBoardAppointmentRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_success() {
        when(repository.search(eq(1),eq(0),eq(null),eq(null))).thenReturn(SearchResponse.builder().build());
        SearchResponse result = defaultSearchME.initialize(1, 0, null, null).get();

        assertThat(result).isNotNull();
    }

    @Test
    public void test_model_null() {
        assertThatThrownBy(() -> defaultSearchME.get())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Model can not be null");
    }
}