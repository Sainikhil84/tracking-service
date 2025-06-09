/**
 * 
 */
package com.tracking.exception.exceptionhandler.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import com.tracking.dto.ErrorDetails;
import com.tracking.exception.TrackingNumberGenerationException;
import com.tracking.exception.exceptionhandler.RestExceptionHandler;

/**
 * 
 */

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {
	
	@InjectMocks
	 private RestExceptionHandler handler;

     @Test
     void testHandleUnApplicationException() {
         String errorMsg = "Tracking Number Generation failed";
         TrackingNumberGenerationException ex = new TrackingNumberGenerationException(errorMsg, null);

         ResponseEntity<ErrorDetails> response = handler.handleUnApplicationException(ex);

         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
     }

     @Test
     void testHandleMissingParams() {
         String paramName = "trackingId missing";
         MissingServletRequestParameterException ex =
                 new MissingServletRequestParameterException(paramName, "String");

         ResponseEntity<Object> response = handler.handleMissingParams(ex);

         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
     }

     @Test
     void testHandleAllException() {
         Exception ex = new Exception("Unexpected error");
         ResponseEntity<ErrorDetails> response = handler.handleAllException(ex);
         assertNotNull(response.getBody());
     
     }
 }

