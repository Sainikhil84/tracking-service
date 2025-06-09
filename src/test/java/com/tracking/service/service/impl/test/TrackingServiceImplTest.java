package com.tracking.service.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import com.tracking.dto.TrackingResponseDto;
import com.tracking.exception.TrackingNumberGenerationException;
import com.tracking.service.impl.TrackingServiceImpl;

@ExtendWith(MockitoExtension.class)
class TrackingServiceImplTest {

	        @Mock
	        private RedisTemplate<String, String> redisTemplate;

	        @InjectMocks
	        private TrackingServiceImpl trackingService;
	        
	        @Mock
	        private ValueOperations<String, String> valueOperations;

	        @Test
	        void testGetTrackingNumberSuccess() {
	        	  when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	        	
	            when(redisTemplate.opsForValue().setIfAbsent(anyString(), anyString(), any()))
	                .thenReturn(true);

	            TrackingResponseDto response = trackingService.getTrackingNumber(
	                "IN", "US", new BigDecimal("2.5"), OffsetDateTime.now(), "cust1", "Customer", "slug"
	            );

	            assertNotNull(response);
	        }

	        @Test
	        void testGetTrackingNumberThrowsException() {
	        	when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	            when(redisTemplate.opsForValue().setIfAbsent(anyString(), anyString(), any()))
	                .thenThrow(new RuntimeException("Redis error"));

	            TrackingNumberGenerationException ex = assertThrows(
	                TrackingNumberGenerationException.class,
	                () -> trackingService.getTrackingNumber(
	                    "IN", "US", new BigDecimal("2.5"), OffsetDateTime.now(), "cust1", "Customer", "slug"
	                )
	            );
	            assertNotNull(ex);
	        }

}
