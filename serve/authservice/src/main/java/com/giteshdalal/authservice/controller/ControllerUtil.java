package com.giteshdalal.authservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gitesh
 */
class ControllerUtil {

	private final static String[] SKIP_PARAMS = { "page", "size" };

	/**
	 * Generates a search query using parameters. Skips params from SKIP_PARAMS.
	 *
	 * @param parameters
	 * 		- MultiValueMap
	 * @return search query (?&param=value&param=value2)
	 */
	static String buildQuery(@RequestParam MultiValueMap<String, String> parameters) {
		StringBuffer query = new StringBuffer("?");
		for (Map.Entry<String, List<String>> e : parameters.entrySet()) {
			if (Arrays.stream(SKIP_PARAMS).anyMatch(s -> s.equalsIgnoreCase(e.getKey()))) {
				continue;
			}
			for (String v : e.getValue()) {
				query.append("&").append(e.getKey()).append("=").append(v);
			}
		}
		return query.toString();
	}
}
