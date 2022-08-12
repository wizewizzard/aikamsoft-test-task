package com.wz.criteria.customer;

import com.wz.dao.CustomerDao;
import com.wz.domain.Customer;
import com.wz.exception.InvalidInputException;
import lombok.Getter;

import java.util.Collection;

public class CustomerWithLastName implements CustomerSearchCriterion {
    @Getter
    private final String lastName;

    public CustomerWithLastName(String lastName) throws InvalidInputException{
        if (lastName == null || lastName.isEmpty())
            throw new InvalidInputException("Customer's last name is mandatory");
        this.lastName = lastName;
    }

    @Override
    public Collection<Customer> search(CustomerDao customerDao) {
        return customerDao.findCustomersByLastName(lastName);
    }
}
