package com.wz.criteria;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class CriterionResult<T> {
    @JsonProperty("criteria")
    private final SearchCriterion<T, ?> criterion;
    private final Collection<T> results;
}
