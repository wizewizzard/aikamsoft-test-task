package com.wz.parsing.criteria;

import com.fasterxml.jackson.databind.JsonNode;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.exception.InvalidInputException;

public class PassiveCustomer implements CriteriaJsonParser{
    @Override
    public boolean isSuitable(JsonNode crNode) {
        return crNode.has("badCustomers") ;
    }

    @Override
    public CustomerSearchCriterion tryParse(JsonNode crNode) throws InvalidInputException {
        if(crNode.get("badCustomers").isInt())
            return new com.wz.criteria.customer.PassiveCustomer(crNode.get("badCustomers").asInt());
        else
            throw new InvalidInputException("Wrong formatting. badCustomers must be integer");
    }
}
