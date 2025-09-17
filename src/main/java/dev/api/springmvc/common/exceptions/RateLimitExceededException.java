package dev.api.springmvc.common.exceptions;

public class RateLimitExceededException extends RuntimeException{
	public RateLimitExceededException(String message) {
		super(message);
	}
}
