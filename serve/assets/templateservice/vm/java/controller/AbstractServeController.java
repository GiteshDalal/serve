/**
 * Generated by Serve Engine
 *
 * @author gitesh
 */
package ${service.group.toLowerCase()}.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ${service.group.toLowerCase()}.exception.BadRequest${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException;
import ${service.group.toLowerCase()}.exception.NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException;
import ${service.group.toLowerCase()}.query.SearchCriteria;
import ${service.group.toLowerCase()}.query.SearchOperation;
import ${service.group.toLowerCase()}.service.BaseServeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @param <RT>
 * 		- Generated Resource Type Class
 * @param <ID>
 * 		- Identifier Type Class
 * @param <S>
 * 		- Implemented BaseServeService Class
 */
public abstract class AbstractServeController<RT extends ResourceSupport, ID, S extends BaseServeService<RT, ID>> {

	private static final String ALLOWED_ROLE = "USER";
	private static final String QUERY_PARAMS_REGEX = "([\\w][\\w\\d_.]*?)[(]([.:!<>~$^*?`]{1,2}|[!]?[\\w]{2,10})[)]([\\s\\d\\w,$@_.-]*)?,";

	@Autowired
	protected S service;

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','" + ALLOWED_ROLE + ":READ')")
	public HttpEntity<RT> getById(@PathVariable("uid") ID uid, Locale locale) {
		RT resource = service.findByUid(uid, locale)
				.orElseThrow(() -> new NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException("Object with uid : '" + uid + "' not found!"));
		this.supportHateoas(resource);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','" + ALLOWED_ROLE + ":READ')")
	public HttpEntity<Page<RT>> getAll(Pageable pageable, @RequestParam(value = "q", required = false) String query, Locale locale) {
		Page<RT> resources = service.findAll(generateSearchCriteria(query), pageable, locale);
		this.supportHateoas(resources.getContent());
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','" + ALLOWED_ROLE + ":WRITE')")
	public HttpEntity<RT> add(@RequestBody RT resource, Locale locale) {
		RT newResource = service.save(resource, locale);
		this.supportHateoas(newResource);
		return new ResponseEntity<>(newResource, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','" + ALLOWED_ROLE + ":WRITE')")
	public HttpEntity<RT> overwrite(@PathVariable("uid") ID uid, @RequestBody RT resource, Locale locale) {
		RT updated = service.update(uid, resource, locale);
		this.supportHateoas(updated);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}

	@PatchMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','" + ALLOWED_ROLE + ":WRITE')")
	public HttpEntity<?> patch(@PathVariable("uid") ID uid, HttpEntity<String> httpEntity, Locale locale) {
		JsonNode jsonNode;
		try {
			jsonNode = new ObjectMapper().readTree(httpEntity.getBody());
		} catch (IOException e) {
			throw new BadRequest${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException("Error translating request body: " + httpEntity.getBody(), e);
		}
		service.patch(uid, jsonNode, locale);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','" + ALLOWED_ROLE + ":DELETE')")
	public HttpEntity<?> delete(@PathVariable("uid") ID uid, Locale locale) {
		service.deleteByUid(uid, locale);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private List<SearchCriteria> generateSearchCriteria(String query) {
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

	protected abstract void supportHateoas(RT resource);

	private void supportHateoas(Collection<RT> resources) {
		if (CollectionUtils.isNotEmpty(resources)) {
			resources.forEach(this::supportHateoas);
		}
	}
}

