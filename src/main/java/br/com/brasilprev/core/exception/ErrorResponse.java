package br.com.brasilprev.core.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private Integer status;
	private String path;
	private String error;
	private Long timestamp;
	private String message;

}
