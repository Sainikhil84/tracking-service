package com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

	/** errorCode */
	private Integer errorCode;

	/** errorMessage */
	private String errorMessage;

	/** errorDescription */
	private String errorDescription;

}
