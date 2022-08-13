package com.wz.parsing.criteria;

import com.fasterxml.jackson.databind.JsonNode;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.exception.InvalidInputException;

public class CustomerOfSpecificProduct implements CriteriaJsonParser{
    @Override
    public boolean isSuitable(JsonNode crNode) {
        return crNode.has("productName") && crNode.has("minTimes");
    }

    @Override
    public CustomerSearchCriterion tryParse(JsonNode crNode) throws InvalidInputException {
        if(crNode.get("productName").isTextual() && crNode.get("minTimes").isInt())
            return new com.wz.criteria.customer.CustomerOfSpecificProduct(crNode.get("productName").asText(), crNode.get("minTimes").asInt());
        else
            throw new InvalidInputException("Wrong formatting. Product name must be textual and minTimes must be type of integer");
    }
}
