/**
 * Copyright (c) 2025 Your TGTech. All rights reserved.
 * 
 * @file TrackingController.java
 * @author nikhil
 * @version 1.0
 */
package com.tracking.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tracking.dto.TrackingResponseDto;
import com.tracking.exception.TrackingNumberGenerationException;
import com.tracking.service.TrackingService;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/** TrackingController */
@RestController
@RequestMapping("/v1")
@Validated
public class TrackingController {

	/** Logger **/
	private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);

	/** TrackingService **/
	private final TrackingService trackingService;

	/**
	 * @param trackingService
	 */
	public TrackingController(TrackingService trackingService) {
		this.trackingService = trackingService;
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
	@GetMapping(value = "/next-tracking-number", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrackingResponseDto> getTrackingNumber(
			@RequestParam("origin_country_id") @NotBlank(message = "origin_country_id is required") @Pattern(regexp = "^[A-Z]{2}$", message = "origin_country_id must be ISO 3166-1 alpha-2 (e.g., 'MY')") String originCountryId,
			@RequestParam("destination_country_id") @NotBlank(message = "destination_country_id is required") @Pattern(regexp = "^[A-Z]{2}$", message = "destination_country_id must be ISO 3166-1 alpha-2 (e.g., 'ID')") String destinationCountryId,
			@RequestParam("weight") @NotNull(message = "weight is required") @DecimalMin(value = "0.001", message = "weight must be at least 0.001 kg") @Digits(integer = 10, fraction = 3, message = "weight must have up to 3 decimal places") BigDecimal weight,
			@RequestParam("created_at") @NotNull(message = "created_at is required") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdAt,
			@RequestParam("customer_id") @NotNull(message = "customer_id is required") String customerId,
			@RequestParam("customer_name") @NotBlank(message = "customer_name is required") String customerName,
			@RequestParam("customer_slug") @NotBlank(message = "customer_slug is required") @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "customer_slug must be in kebab-case (e.g., 'redbox-logistics')") String customerSlug) {
		logger.info("TrackingController::getTrackingNumber");
		TrackingResponseDto trackingResponseDto = this.trackingService.getTrackingNumber(originCountryId,
				destinationCountryId, weight, createdAt, customerId, customerName, customerSlug);
		logger.info("TrackingController::getTrackingNumber::trackingResponseDto::{}", trackingResponseDto);
		if (trackingResponseDto == null) {
			throw new TrackingNumberGenerationException("Failed to generate tracking number.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(trackingResponseDto);

	}

}
