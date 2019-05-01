package com.giteshdalal.authservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.giteshdalal.authservice.query.SearchCriteria;
import com.giteshdalal.authservice.query.SearchOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gitesh
 */
class ControllerUtil {

	private static final String QUERY_PARAMS_REGEX = "([\\w][\\w\\d_.]*?)[(]([.:!<>~$^*?`]{1,2}|[!]?[\\w]{2,10})[)]([\\s\\d\\w,$@_.-]*)?,";

	/**
	 * Generate JPA query params using query string
	 *
	 * @param query
	 * 		- search query string
	 * @return JPA query params
	 */
	static List<SearchCriteria> generateSearchCriteria(String query) {
		List<SearchCriteria> queryParams = new ArrayList<>();
		if (StringUtils.isNotBlank(query)) {
			Pattern pattern = Pattern.compile(QUERY_PARAMS_REGEX);
			Matcher matcher = pattern.matcher(query.trim() + ",");
			while (matcher.find()) {
				SearchCriteria criteria = new SearchCriteria();
				criteria.setKey(matcher.group(1));
				criteria.setOperation(SearchOperation.getOperation(matcher.group(2).toLowerCase()));
				criteria.setValue(matcher.group(3));
				queryParams.add(criteria);
			}
		}

		return queryParams;
	}
}
