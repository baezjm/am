package com.appointment.manager.api.entrypoints.jmh.search.impl;

import com.appointment.manager.api.core.entities.Paging;
import com.appointment.manager.api.core.entities.SearchResponse;
import com.appointment.manager.api.core.usecase.ValidateUtil;
import com.appointment.manager.api.core.usecase.jmh.search.SearchJMH;
import com.appointment.manager.api.entrypoints.jm.search.SearchJMEndpoint;
import com.appointment.manager.api.entrypoints.jmh.search.SearchJMHEndpoint;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;
import util.java.config.config.Config;
import util.java.config.exception.BadRequestException;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static util.spark.config.Application.APP;

@RunWith(MockitoJUnitRunner.class)
public class DefaultSearchJMHEndpointTest {

    @InjectMocks
    private DefaultSearchJMHEndpoint endpoint;

    @Mock
    private SearchJMH searchJMH;

    @Mock
    Request request;

    @Mock
    Response response;

    @Spy
    private ValidateUtil validateUtil;

    @Before
    public void setUp() throws Exception {
        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(SearchJMH.class).toInstance(searchJMH);
            }
        };

        Config.addInjector(APP, Guice.createInjector(module));
    }

    @Test
    public void test_success() {
        when(request.queryParams(eq("limit"))).thenReturn("1");
        when(request.queryParams(eq("offset"))).thenReturn("0");
        when(searchJMH.initialize(eq(1),eq(0),eq(null),eq(null))).thenReturn(searchJMH);
        when(searchJMH.get()).thenReturn(SearchResponse.builder().results(new ArrayList<>()).paging(Paging.builder().limit(1).offset(0).total(3L).build()).build());

        SearchJMHEndpoint.SearchJMHResponseModel result = endpoint.execute(request, response);

        assertThat(result).isNotNull();
        assertThat(result.getPaging()).isNotNull();
        assertThat(result.getResults()).isNotNull();
    }

    @Test
    public void validation_error_limit_null() {
        when(request.queryParams(eq("limit"))).thenReturn(null);
        when(request.queryParams(eq("offset"))).thenReturn("0");


        assertThatThrownBy(() -> endpoint.execute(request, response))
                .isExactlyInstanceOf(BadRequestException.class)
                .hasMessage("limit can not be null");
    }

    @Test
    public void validation_error_limit_negative() {
        when(request.queryParams(eq("limit"))).thenReturn("-1");
        when(request.queryParams(eq("offset"))).thenReturn("0");


        assertThatThrownBy(() -> endpoint.execute(request, response))
                .isExactlyInstanceOf(BadRequestException.class)
                .hasMessage("limit can not be less then 0");
    }

    @Test
    public void validation_error_offset_null() {
        when(request.queryParams(eq("limit"))).thenReturn("1");
        when(request.queryParams(eq("offset"))).thenReturn(null);

        assertThatThrownBy(() -> endpoint.execute(request, response))
                .isExactlyInstanceOf(BadRequestException.class)
                .hasMessage("offset can not be null");
    }

    @Test
    public void validation_error_offset_negative() {
        when(request.queryParams(eq("limit"))).thenReturn("1");
        when(request.queryParams(eq("offset"))).thenReturn("-1");

        assertThatThrownBy(() -> endpoint.execute(request, response))
                .isExactlyInstanceOf(BadRequestException.class)
                .hasMessage("offset can not be less then 0");
    }

    @Test
    public void validation_error_limit_lower_than_or_equals_limit() {
        when(request.queryParams(eq("limit"))).thenReturn("10000");
        when(request.queryParams(eq("offset"))).thenReturn("1");

        assertThatThrownBy(() -> endpoint.execute(request, response))
                .isExactlyInstanceOf(BadRequestException.class)
                .hasMessage("limit can not be higher than 100");
    }
}