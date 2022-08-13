package com.wz.parsing.criteria;

import com.fasterxml.jackson.databind.JsonNode;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.exception.InvalidInputException;

public interface CriteriaJsonParser {
    boolean isSuitable(JsonNode node);
    CustomerSearchCriterion tryParse(JsonNode node) throws InvalidInputException;
}
