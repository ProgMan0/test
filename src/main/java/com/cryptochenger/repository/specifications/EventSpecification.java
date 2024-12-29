package com.cryptochenger.repository.specifications;

import com.cryptochenger.entity.Event;
import com.cryptochenger.entity.RequestFilter;
import com.cryptochenger.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.function.Predicate;

public interface EventSpecification {

    static Specification<Event> getEventsByFilter(RequestFilter filter) {
        return Specification.where(isEquals("id", filter.getEventId()))
                .and(isEqualsCreatorId("username", filter.getCreatorUsername()));
    }

    static <T> Specification<Event> isEquals(String fieldName, T object) {
        return (root, query, criteriaBuilder) -> {
            if (object == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get(fieldName), object);
        };
    }

    private static <T> Specification<Event> isEqualsCreatorId(String fieldName, String object) {
        return (root, query, criteriaBuilder) -> {
            if (object == null) {
                return null;
            }

            Join<Event, User> join = root.join("owner", JoinType.INNER);

            return criteriaBuilder.equal(join.get(fieldName), object);
        };
    }

    static Specification<Event> isEquals(String owner) {
        return (root, query, builder) -> {
            if (owner == null) {
                return null;
            }

            Join<Event, User> eventUserJoin = root.join("owner", JoinType.INNER);
            return builder.equal(eventUserJoin.get("username"), owner);
        };
    }
}
