package com.account.validation;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Anshul
 *This is response model of the request
 */
@Component
public class AccountValidationResponse {
	
	List<DataProviders> result;

	public List<DataProviders> getResult() {
		return result;
	}

	public void setResult(List<DataProviders> result) {
		this.result = result;
	}

	public AccountValidationResponse(List<DataProviders> result) {
		super();
		this.result = result;
	}

	public AccountValidationResponse() {
		super();
	}
	
	

}
