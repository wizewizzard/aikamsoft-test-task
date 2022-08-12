package com.wz.criteria.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wz.dao.CustomerDao;
import com.wz.domain.Customer;
import com.wz.exception.InvalidInputException;
import lombok.Getter;

import java.util.Collection;


public class CustomerSpentTotalOfRange implements CustomerSearchCriterion {
    @Getter
    @JsonProperty("minExpenses")
    private int minTotalSum;
    @Getter
    @JsonProperty("maxExpenses")
    private int maxTotalSum;

    public CustomerSpentTotalOfRange(int minTotalSum, int maxTotalSum) throws InvalidInputException {
        if (minTotalSum < 0 || maxTotalSum < 0)
            throw new InvalidInputException("Total sums must not be less than 0");
        if (maxTotalSum < minTotalSum)
            throw new InvalidInputException("Max total sum must not be greater than min total sum");
        this.minTotalSum = minTotalSum;
        this.maxTotalSum = maxTotalSum;
    }

    @Override
    public Collection<Customer> search(CustomerDao dataSource) {
        return dataSource.findCustomersInRangeOfPurchaseSum(minTotalSum, maxTotalSum);
    }
}
