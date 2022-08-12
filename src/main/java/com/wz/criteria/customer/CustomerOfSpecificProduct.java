package com.wz.criteria.customer;

import com.wz.dao.CustomerDao;
import com.wz.domain.Customer;
import com.wz.exception.InvalidInputException;
import lombok.Getter;

import java.util.Collection;

public class CustomerOfSpecificProduct implements CustomerSearchCriterion{
    @Getter
    private String productName;
    @Getter
    private int minTimes;

    public CustomerOfSpecificProduct(String productName, int minTimes) throws InvalidInputException {
        if (productName == null || productName.isEmpty())
            throw new InvalidInputException("Product name must not be empty");
        this.productName = productName;
        this.minTimes = minTimes;
    }

    @Override
    public Collection<Customer> search(CustomerDao dataSource) {
        return dataSource.findCustomersByProductTheyBoughtAtLeastNTimes(productName, minTimes);
    }
}
