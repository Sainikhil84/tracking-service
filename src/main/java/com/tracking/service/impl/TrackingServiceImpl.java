/**
 * Copyright (c) 2025 Your TGTech. All rights reserved.
 * 
 * @file TrackingServiceImpl.java
 * @author nikhil
 * @version 1.0
 */
package com.tracking.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tracking.constants.ErrorConstants;
import com.tracking.dto.TrackingResponseDto;
import com.tracking.exception.TrackingNumberGenerationException;
import com.tracking.service.TrackingService;

/** TrackingServiceImpl */
@Service
public class TrackingServiceImpl implements TrackingService {

	/** Logger **/
	private static final Logger logger = LoggerFactory.getLogger(TrackingServiceImpl.class);

	/** CHARACTERS */
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private Set<String> checkDuplicates = ConcurrentHashMap.newKeySet();

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
	@Override
	public TrackingResponseDto getTrackingNumber(String originCountryId, String destinationCountryId, BigDecimal weight,
			OffsetDateTime createdAt, String customerId, String customerName, String customerSlug) {
		logger.info("TrackingServiceImpl::getTackingNumber");
		try {
			String trackingNumber = generateTrackingNumber(originCountryId, destinationCountryId);
			logger.info("TrackingServiceImpl::getTrackingNumber::trackingNumber::{}", trackingNumber);
			return TrackingResponseDto.builder().trackingNumber(trackingNumber).createdAt(OffsetDateTime.now())
					.status("Success").build();
		} catch (Exception e) {
			logger.error("TrackingServiceImpl::getTrackingNumber::catch block::{}", e);
			throw new TrackingNumberGenerationException(ErrorConstants.COMMON_ERROR_01,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param originCountryId
	 * @param destinationCountryId
	 * @return
	 */
	private String generateTrackingNumber(String originCountryId, String destinationCountryId) {
		try {
			logger.info("TrackingServiceImpl::generateTrackingNumber");
			StringBuilder sb = new StringBuilder();
			var origin = originCountryId.toUpperCase().replaceAll("[^A-Z]", "");
			var destination = destinationCountryId.toUpperCase().replaceAll("[^A-Z]", "");
			var fixedLength = origin.length() + destination.length();
			var randomLength = 16 - fixedLength;
			StringBuilder randomString = new StringBuilder();
			ThreadLocalRandom random = ThreadLocalRandom.current();
			for (int i = 0; i < randomLength; i++) {
				int index = random.nextInt(CHARACTERS.length());
				randomString.append(CHARACTERS.charAt(index));
			}
			sb.append(origin);
			sb.append(randomString);
			sb.append(destination);
			String trackingNumber = sb.toString();
			if (checkDuplicates.contains(trackingNumber)) {
				throw new TrackingNumberGenerationException(ErrorConstants.DUPLICATE_TRACKING_NUMBER,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			checkDuplicates.add(trackingNumber);
			logger.info("TrackingServiceImpl::generateTrackingNumber::trackingNumber::{}", trackingNumber);
			return trackingNumber;
		} catch (Exception e) {
			logger.error("TrackingServiceImpl::getTrackingNumber::catch block::{}", e);
			throw new TrackingNumberGenerationException(ErrorConstants.COMMON_ERROR_02,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
