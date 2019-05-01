package com.giteshdalal.authservice.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.giteshdalal.authservice.exceptions.BadRequestAuthServiceException;
import org.springframework.data.jpa.domain.Specification;

/**
 * @param <T>
 * 		- Generated Model Class
 * @author gitesh
 */
public class ModelSpecification<T> implements Specification<T> {

	public static final Class[] SUPPORTED_CLASSES = { Long.class, String.class, Boolean.class, Float.class, Integer.class, Double.class,
			Float.class, Date.class };
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	private SearchCriteria criteria;

	public ModelSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Path path = getKey(root);
		switch (criteria.getOperation()) {
		case EQUALITY:
			return builder.equal(path, convertValue(criteria.getValue().toString(), path.getJavaType()));
		case NEGATION:
			return builder.notEqual(path, convertValue(criteria.getValue().toString(), path.getJavaType()));
		case GREATER_THAN:
			return builder.greaterThan(path, criteria.getValue().toString());
		case LESS_THAN:
			return builder.lessThan(path, criteria.getValue().toString());
		case GREATER_THAN_EQ:
			return builder.greaterThanOrEqualTo(path, criteria.getValue().toString());
		case LESS_THAN_EQ:
			return builder.lessThanOrEqualTo(path, criteria.getValue().toString());
		case LIKE:
			return builder.like(path, criteria.getValue().toString());
		case STARTS_WITH:
			return builder.like(path, criteria.getValue() + "%");
		case ENDS_WITH:
			return builder.like(path, "%" + criteria.getValue());
		case CONTAINS:
			return builder.like(path, "%" + criteria.getValue() + "%");
		case IS_EMPTY:
			return builder.isEmpty(path);
		case IS_NOT_EMPTY:
			return builder.isNotEmpty(path);
		case IN:
			return addInValues(builder.in(path), path.getJavaType());
		case NOT_IN:
			return builder.not(addInValues(builder.in(path), path.getJavaType()));
		case IS_MEMBER:
			return builder.isMember(convertValue(criteria.getValue().toString(), path.getJavaType()), path);
		case IS_NOT_MEMBER:
			return builder.isNotMember(convertValue(criteria.getValue().toString(), path.getJavaType()), path);
		default:
			return null;
		}
	}

	private Path getKey(Root<T> root) {
		Iterator<String> keys = Arrays.asList(criteria.getKey().split("[.]")).iterator();
		String k = keys.next();
		Join<Object, Object> pathJoin = null;
		while (keys.hasNext()) {
			pathJoin = Objects.nonNull(pathJoin) ? pathJoin.join(k) : root.join(k);
			k = keys.next();
		}
		return Objects.nonNull(pathJoin) ? pathJoin.get(k) : root.get(k);
	}

	private Object convertValue(String value, Class type) {
		try {
			if (type.equals(Long.class)) {
				return Long.parseLong(value);        // Long
			} else if (type.equals(Boolean.class)) {
				return Boolean.parseBoolean(value);  // Boolean
			} else if (type.equals(String.class)) {
				return value;                        // String
			} else if (type.equals(Integer.class)) {
				return Integer.parseInt(value);      // Integer
			} else if (type.equals(Double.class)) {
				return Double.parseDouble(value);    // Double
			} else if (type.equals(Float.class)) {
				return Float.parseFloat(value);      // Float
			} else if (type.equals(Date.class)) {
				return new SimpleDateFormat(DATE_FORMAT).parse(value);   // DATE
			}
		} catch (ParseException | NumberFormatException e) {
			throw new BadRequestAuthServiceException("Unable to parse. Field value is in invalid format.", e);
		}
		return value;
	}

	private CriteriaBuilder.In addInValues(CriteriaBuilder.In builderIn, Class type) {
		String[] values = criteria.getValue().toString().split(",");
		CriteriaBuilder.In in = builderIn;
		for (String v : values) {
			in = in.value(convertValue(v, type));
		}
		return in;
	}
}
