package br.com.prevenda.javaclient;

import br.com.prevenda.handler.RestResponseExceptionHandler;
import br.com.prevenda.model.Client;
import br.com.prevenda.model.PageableResponse;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {
	private RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/v1/protected/students")
			.basicAuthorization("toyo", "devdojo").errorHandler(new RestResponseExceptionHandler()).build();
	private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
			.rootUri("http://localhost:8080/v1/admin/students").basicAuthorization("toyo", "devdojo")
			.errorHandler(new RestResponseExceptionHandler()).build();

	public Client findById(long id) {
		return restTemplate.getForObject("/{id}", Client.class, id);
	}

	public List<Client> listAll() {
		ResponseEntity<PageableResponse<Client>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
				new ParameterizedTypeReference<PageableResponse<Client>>() {
				});
		return exchange.getBody().getContent();
	}

	public Client save(Client student) {
		ResponseEntity<Client> exchangePost = restTemplateAdmin.exchange("/", HttpMethod.POST,
				new HttpEntity<>(student, createJSONHeader()), Client.class);
		return exchangePost.getBody();
	}

	public void update(Client student) {
		restTemplateAdmin.put("/", student);
	}

	public void delete(long id) {
		restTemplateAdmin.delete("/{id}", id);
	}

	private static HttpHeaders createJSONHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
