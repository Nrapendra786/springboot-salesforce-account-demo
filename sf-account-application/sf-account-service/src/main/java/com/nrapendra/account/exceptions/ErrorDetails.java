package com.nrapendra.account.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
	private final Date timestamp;
	private final String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

}
