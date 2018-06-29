package com.giteshdalal.productservice.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author gitesh
 *
 */
@FeignClient("auth-service")
public interface AuthService {

	@RequestMapping(value = "/oauth/token_key", method = RequestMethod.GET)
	Map<String, String> hello(@RequestHeader("Authorization") String authorizationHeaderValue);

}
