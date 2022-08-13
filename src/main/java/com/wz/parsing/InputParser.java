package com.wz.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.dto.CustomerSearchRequest;
import com.wz.dto.StatRequest;
import com.wz.exception.InvalidInputException;
import com.wz.parsing.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class InputParser {
    private final ObjectMapper mapper;

    public CustomerSearchRequest parseSearch(Reader inputStream, List<CriteriaJsonParser> parsers){
        try {
            JsonNode root = mapper.readTree(inputStream);
            if(root.has("criterias") && root.get("criterias").isArray()){
                JsonNode criteriasNode = root.get("criterias");
                List<CustomerSearchCriterion> criterias = new ArrayList<>();
                criteriasNode.forEach(crNode -> {
                    CriteriaJsonParser parser = parsers.stream()
                            .filter(p -> p.isSuitable(crNode))
                            .findFirst()
                            .orElseThrow(() -> new InvalidInputException("Invalid JSON input file formatting. Unknown search type is given"));
                    criterias.add(parser.tryParse(crNode));
                });
                return new CustomerSearchRequest(criterias);
            }
            else{
                log.error("Invalid JSON formatting. Root is not an object or it does not have criterias field specified");
                throw new InvalidInputException("Invalid JSON input file formatting." +
                        " Root is not an object, or it does not have criterias field specified.");
            }
        }
        catch (IOException exception){
            log.error("Error when parsing for stat operation", exception);
            throw new InvalidInputException("Invalid JSON input file formatting");
        }
    }

    /**
     * Parse using default set of parsers
     * @param inputStream
     * @return
     */
    public CustomerSearchRequest parseSearch(Reader inputStream){
        List<CriteriaJsonParser> parsers = new ArrayList<>();
        parsers.add(new CustomerWithLastName());
        parsers.add(new CustomerSpentTotalOfRange());
        parsers.add(new CustomerOfSpecificProduct());
        parsers.add(new PassiveCustomer());
        return parseSearch(inputStream, parsers);
    }

    public StatRequest parseStat(Reader inputStream){
        try{
            JsonNode root = mapper.readTree(inputStream);
            if(root.has("startDate") && root.has("endDate")){
                String startDate = root.get("startDate").asText();
                String endDate = root.get("endDate").asText();
                try{
                    return new StatRequest(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE), LocalDate.parse(endDate));
                }
                catch (DateTimeParseException exception){
                    log.error("Invalid JSON formatting. Date must have an ISO formatting");
                    throw new InvalidInputException("Invalid JSON input file formatting. ISO date formatting required.");
                }
            }
            else{
                log.error("Invalid JSON formatting. Root is not an object or it does not have startDate and endDate specified");
                throw new InvalidInputException("Invalid JSON input file formatting." +
                        " Root is not an object, or it does not have startDate and endDate specified.");
            }
        }
        catch (IOException exception){
            log.error("Error when parsing for stat operation", exception);
            throw new InvalidInputException("Invalid JSON input file formatting");
        }

    }
}
