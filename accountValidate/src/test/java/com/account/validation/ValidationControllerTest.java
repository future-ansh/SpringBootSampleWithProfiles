package com.account.validation;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ValidationController.class)
@RunWith(SpringRunner.class)
public class ValidationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DataProviders provider;

	@MockBean
	private AccountValidationResponse AccountValidationResponse;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private MockRestServiceServer mockServer;

	private ObjectMapper mapper = new ObjectMapper();

	String jsonWithValidProvider = "{\"accountNumber\": \"12345678\"," + "\"providers\": [" + "\"provider1\"" + "]"
			+ "}";

	@Before
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);

	}

	@Test
	public void Test() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		new HttpEntity<>("12345678", headers);

		mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/provider1")))
				.andExpect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(true)));
		

		mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/provider2")))
				.andExpect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(true)));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/validate")
				.accept(MediaType.APPLICATION_JSON).content(jsonWithValidProvider)
				.contentType(MediaType.APPLICATION_JSON);
		;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		Assert.notNull(result);
	}
}
