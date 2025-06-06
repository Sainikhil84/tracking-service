/**
 * Copyright (c) 2025 Your TGTech. All rights reserved.
 * 
 * @file TrackingResponseDto.java
 * @author nikhi
 * @version 1.0
 */
package com.tracking.dto;

import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * TrackingResponseDto
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TrackingResponseDto {

	@JsonProperty("tracking_number")
	/** trackingNumber */
	private String trackingNumber;

	@JsonProperty("created_at")
	/** createdAt */
	private OffsetDateTime createdAt;

	/** status */
	private String status;

}
