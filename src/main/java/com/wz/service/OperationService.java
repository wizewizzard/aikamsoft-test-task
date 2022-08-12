package com.wz.service;

import com.wz.criteria.CriterionResult;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.dao.CustomerDao;
import com.wz.domain.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OperationService {
    private final CustomerDao customerDao;

    public OperationService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<CriterionResult<Customer>> search(List<CustomerSearchCriterion> criteria) {
        List<CriterionResult<Customer>> criterionResults = new ArrayList<>();
        for (CustomerSearchCriterion criterion : criteria) {
            criterionResults.add(new CriterionResult<>(criterion, criterion.search(customerDao)));
        }
        return criterionResults;
    }

    public Object stat(Object fromDateIncluded, Object untilDateIncluded) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
