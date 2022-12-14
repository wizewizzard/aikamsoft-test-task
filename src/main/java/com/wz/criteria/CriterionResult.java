package com.wz.criteria;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({ "criteria", "results"})
public class CriterionResult<T> {
    @JsonProperty("criteria")
    private final SearchCriterion<T, ?> criterion;
    private final Collection<T> results;
}
