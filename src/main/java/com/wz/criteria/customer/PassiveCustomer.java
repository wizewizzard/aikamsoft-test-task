package com.wz.criteria.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wz.dao.CustomerDao;
import com.wz.domain.Customer;
import com.wz.exception.InvalidInputException;
import lombok.Getter;

import java.util.Collection;

public class PassiveCustomer implements CustomerSearchCriterion {
    @Getter
    @JsonProperty("badCustomers")
    private int passiveCustomers;

    public PassiveCustomer(int passiveCustomers) throws InvalidInputException {
        if (passiveCustomers < 0)
            throw new InvalidInputException("Customers count must not be less than 0");
        this.passiveCustomers = passiveCustomers;
    }

    @Override
    public Collection<Customer> search(CustomerDao dataSource) {
        return dataSource.findPassiveCustomers(passiveCustomers);
    }
}
