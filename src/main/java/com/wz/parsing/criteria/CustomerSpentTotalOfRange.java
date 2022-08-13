package com.wz.parsing.criteria;

import com.fasterxml.jackson.databind.JsonNode;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.exception.InvalidInputException;

public class CustomerSpentTotalOfRange implements CriteriaJsonParser{
    @Override
    public boolean isSuitable(JsonNode crNode) {
        return crNode.has("minExpenses") && crNode.has("maxExpenses") ;
    }

    @Override
    public CustomerSearchCriterion tryParse(JsonNode crNode) throws InvalidInputException {
        if(crNode.get("minExpenses").isInt() && crNode.get("maxExpenses").isInt())
            return new com.wz.criteria.customer.CustomerSpentTotalOfRange(crNode.get("minExpenses").asInt(), crNode.get("maxExpenses").asInt());
        else
            throw new InvalidInputException("Wrong formatting. minExpenses and maxExpenses must be type of integer");
    }
}
