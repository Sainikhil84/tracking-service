/**
 * Copyright (c) 2025 Your TGTech. All rights reserved.
 * 
 * @file TrackingService.java
 * @author nikhil
 * @version 1.0
 */
package com.tracking.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import com.tracking.dto.TrackingResponseDto;

/** TrackingService */
public interface TrackingService {

	/**
	 * @param originCountryId
	 * @param destinationCountryId
	 * @param weight
	 * @param createdAt
	 * @param customerId
	 * @param customerName
	 * @param customerSlug
	 * @return
	 */
	TrackingResponseDto getTrackingNumber(String originCountryId, String destinationCountryId, BigDecimal weight,
			OffsetDateTime createdAt, String customerId, String customerName, String customerSlug);
	
}