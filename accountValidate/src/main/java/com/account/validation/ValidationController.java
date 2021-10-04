package com.account.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Anshul
 *
 */
@RestController
public class ValidationController {

	@Value("#{${provider.list}}")
	private Map<String, String> providerList;

	@Autowired
	private DataProviders provider;

	@Autowired
	private AccountValidationResponse response;

	/**
	 * API will check with dataproviders is account is valid or not. It will pick dataproviders endpoint
	 * from properties file dynamically 
	 * @param req 
	 * @return AccountValidationResponse
	 */
	@PostMapping("/validate")
	public AccountValidationResponse validateAccount(@Valid @RequestBody AccountValidationRequest req) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(req.getAccountNumber(), headers);

		RestTemplate restTemplate = new RestTemplate();
		List<DataProviders> result = new ArrayList<DataProviders>();

		req.getProviders().forEach(provider -> {
			System.out.println("Provider : " + provider);
			Boolean isValid = restTemplate.postForObject(providerList.get(provider), httpEntity, Boolean.class);
			DataProviders temp = new DataProviders(provider, false);

			result.add(new DataProviders(provider, isValid));
		});

		response.setResult(result);

		return response;

	}
	/*These are dummy implementation to verify request and response on embedded Tomcat . Commented as these are not required
	 * 
	 * @PostMapping("/provider1") public Boolean
	 * validateAcctWithProvider1( @RequestBody String request) { return true; }
	 * 
	 * @PostMapping("/provider2") public Boolean
	 * validateAcctWithProvider2( @RequestBody String request) { return false; }
	 */

}
