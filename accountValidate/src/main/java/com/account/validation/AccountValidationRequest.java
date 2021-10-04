package com.account.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;
import org.springframework.util.CollectionUtils;


/**
 * @author Anshul
 * This is the Request Model for account validation
 * Request will be rejected id account number is null
 * It will check with all data providers in case Data provider name is not provided in request
 * 
 */
public class AccountValidationRequest {

	@NotBlank
	public String accountNumber;

	public List<String> providers = new ArrayList<String>(Arrays.asList("provider1", "provider2"));

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<String> getProviders() {
		return providers;
	}

	public void setProviders(List<String> providers) {
		this.providers = providers;
	}

	public AccountValidationRequest(String accountNumber, List<String> providers) {
		super();
		this.accountNumber = accountNumber;
		System.out.println(" Test " + this.providers);
		this.providers = CollectionUtils.isEmpty(providers) ? this.providers : providers;
		System.out.println(" Test1 " + this.providers);
		System.out.println(" Test2 " + providers);
	}

}
