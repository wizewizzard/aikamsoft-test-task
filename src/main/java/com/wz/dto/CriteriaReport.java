package com.wz.dto;

import com.wz.criteria.CriterionResult;
import com.wz.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CriteriaReport {
    private String type;
    private List<CriterionResult<Customer>> results;
}
