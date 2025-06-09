package com.tracking.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tracking.controller.TrackingController;
import com.tracking.dto.TrackingResponseDto;
import com.tracking.exception.TrackingNumberGenerationException;
import com.tracking.service.TrackingService;



@ExtendWith(MockitoExtension.class)
class TrackingControllerTest {

	@InjectMocks
    private TrackingController trackingController;
	
	@Mock
    private TrackingService trackingService;
	
    @Test
    void testGetTrackingNumberSuccess() {
        TrackingResponseDto responseDto = new TrackingResponseDto();
        when(trackingService.getTrackingNumber(
                anyString(), anyString(), any(BigDecimal.class),
                any(OffsetDateTime.class), anyString(), anyString(), anyString()))
            .thenReturn(responseDto);

        ResponseEntity<TrackingResponseDto> response = trackingController.getTrackingNumber(
                "MY", "ID", new BigDecimal("1.234"),
                OffsetDateTime.now(), "cust123", "Customer Name", "customer-slug");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(trackingService, times(1)).getTrackingNumber(
                eq("MY"), eq("ID"), eq(new BigDecimal("1.234")),
                any(OffsetDateTime.class), eq("cust123"), eq("Customer Name"), eq("customer-slug"));
    }

    @Test
    void testGetTrackingNumberExceptionCase() {
        when(trackingService.getTrackingNumber(
                anyString(), anyString(), any(BigDecimal.class),
                any(OffsetDateTime.class), anyString(), anyString(), anyString()))
            .thenReturn(null);

        TrackingNumberGenerationException exception = assertThrows(
                TrackingNumberGenerationException.class,
                () -> trackingController.getTrackingNumber(
                        "MY", "ID", new BigDecimal("1.234"),
                        OffsetDateTime.now(), "cust123", "Customer Name", "customer-slug")
        );
       
        assertNotNull(exception);
    }

}