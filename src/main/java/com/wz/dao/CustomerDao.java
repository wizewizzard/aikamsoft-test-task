package com.wz.dao;

import com.wz.domain.Customer;
import com.wz.exception.DataAccessException;

import java.util.List;

public interface CustomerDao {
    List<Customer> findCustomersByLastName(String lastName) throws DataAccessException;

    List<Customer> findCustomersByProductTheyBoughtAtLeastNTimes(String productName, int times) throws DataAccessException;

    List<Customer> findCustomersInRangeOfPurchaseSum(int minSum, int maxSum) throws DataAccessException;

    List<Customer> findPassiveCustomers(int count) throws DataAccessException;
}
