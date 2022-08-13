package com.wz.parsing.criteria;

import com.fasterxml.jackson.databind.JsonNode;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.exception.InvalidInputException;

public class CustomerWithLastName implements CriteriaJsonParser{
    public boolean isSuitable(JsonNode crNode){
        return crNode.has("lastName");
    }
    public CustomerSearchCriterion tryParse(JsonNode crNode){
        if(crNode.get("lastName").isTextual())
            return new com.wz.criteria.customer.CustomerWithLastName(crNode.get("lastName").asText());
        else
            throw new InvalidInputException("Wrong formatting. Lastname must be textual");
    }
}
