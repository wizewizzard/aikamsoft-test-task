package com.wz.dto;

import com.wz.criteria.customer.CustomerSearchCriterion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CustomerSearchRequest {
    private List<CustomerSearchCriterion> criterias;
}
