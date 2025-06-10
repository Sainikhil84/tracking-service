/**
 * Copyright (c) 2025. All rights reserved.
 * 
 * @file TrackingServiceImpl.java
 * @author nikhil
 * @version 1.0
 */
package com.tracking.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.tracking.constants.Constants;
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

	/**
	 * redisTemplate.
	 */
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * @param redisTemplate
	 */
	public TrackingServiceImpl(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

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

			String origin = originCountryId.toUpperCase().replaceAll("[^A-Z]", "");
			String destination = destinationCountryId.toUpperCase().replaceAll("[^A-Z]", "");
			int fixedLength = origin.length() + destination.length();
			int randomLength = 16 - fixedLength;
			ThreadLocalRandom random = ThreadLocalRandom.current();
			while (true) {
				StringBuilder randomString = new StringBuilder();
				for (int i = 0; i < randomLength; i++) {
					int index = random.nextInt(CHARACTERS.length());
					randomString.append(CHARACTERS.charAt(index));
				}
				StringBuilder sb = new StringBuilder();
				sb.append(origin);
				sb.append(randomString);
				sb.append(destination);
				String trackingNumber = sb.toString();
				logger.info("TrackingServiceImpl::generateTrackingNumber::trackingNumber::{}", trackingNumber);
				String redisKey = generateCacheKey(trackingNumber);
				logger.info("TrackingServiceImpl::generateTrackingNumber::redisKey::{}", redisKey);
				Boolean success = redisTemplate.opsForValue().setIfAbsent(redisKey, "number", Duration.ofDays(90));
				if (Boolean.TRUE.equals(success)) {
					return trackingNumber;
				}
			}
		} catch (Exception e) {
			logger.error("TrackingServiceImpl::generateTrackingNumber::catch block::{}", e);
			throw new TrackingNumberGenerationException(ErrorConstants.COMMON_ERROR_02,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param trackingNumber
	 * @return
	 */
	private String generateCacheKey(String trackingNumber) {
		logger.info("TrackingServiceImpl::generateCacheKey");
		try {
			return new StringBuilder(Constants.TRACKING_NUMBER).append(Constants.UNDERSCORE).append(trackingNumber)
					.toString();
		} catch (Exception ex) {
			logger.error("TrackingServiceImpl::generateCacheKey::catch block::{}", ex);
			throw new TrackingNumberGenerationException(ErrorConstants.CACHE_KEY_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
