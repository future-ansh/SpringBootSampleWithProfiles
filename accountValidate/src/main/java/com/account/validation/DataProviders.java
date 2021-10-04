package com.account.validation;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Anshul
 * 
 */
@Component
public class DataProviders {

	private String provider;
	private boolean isValid;

	public DataProviders() {
		super();
	}

	public DataProviders(String provider, boolean isValid) {

		this.provider = provider;
		this.isValid = isValid;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
