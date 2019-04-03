package com.giteshdalal.productservice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RemoteAuthTokenServices implements ResourceServerTokenServices {

	@Setter
	private RestOperations restTemplate;

	@Setter
	private String checkTokenEndpointUrl;

	@Setter
	private String clientId;

	@Setter
	private String clientSecret;

	@Setter
	private String tokenName = "token";

	@Setter
	private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

	public RemoteAuthTokenServices() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Ignore 400
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getRawStatusCode() != 400) {
					super.handleError(response);
				}
			}
		});

		this.setRestTemplate(restTemplate);
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add(tokenName, accessToken);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));
		Map<String, Object> map = postForMap(checkTokenEndpointUrl, formData, headers);

		if (map.containsKey("error")) {
			log.debug("check_token returned error: " + map);
			throw new InvalidTokenException(String.valueOf(map.get("error_description")));
		}

		if (!Boolean.TRUE.toString().equalsIgnoreCase(map.get("active").toString())) {
			log.debug("check_token returned active attribute: " + map);
			throw new InvalidTokenException("User account not enabled");
		}

		return tokenConverter.extractAuthentication(map);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

	private String getAuthorizationHeader(String clientId, String clientSecret) {

		if (clientId == null || clientSecret == null) {
			log.warn("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
		}

		String credentials = String.format("%s:%s", clientId, clientSecret);
		return "Basic " + new String(Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8)));
	}

	private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
		if (Objects.isNull(headers.getContentType())) {
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		}
		if (CollectionUtils.isEmpty(headers.getAccept())) {
			headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		}
		return restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<>(formData, headers), Map.class).getBody();
	}

}
