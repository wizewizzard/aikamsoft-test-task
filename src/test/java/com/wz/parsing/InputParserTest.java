package com.wz.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.criteria.customer.CustomerOfSpecificProduct;
import com.wz.criteria.customer.CustomerSpentTotalOfRange;
import com.wz.criteria.customer.CustomerWithLastName;
import com.wz.criteria.customer.PassiveCustomer;
import com.wz.dto.CustomerSearchRequest;
import com.wz.dto.StatRequest;
import com.wz.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class InputParserTest {

    @Test
    void parseSearch() {
    }

    @Test
    void shouldParseStatJsonInput() {
        String data = "{\"startDate\": \"2020-01-14\", \"endDate\": \"2020-01-26\"}";
        StringReader stringReader = new StringReader(data);
        ObjectMapper mapper = new ObjectMapper();
        InputParser parser = new InputParser(mapper);
        StatRequest request = parser.parseStat(new BufferedReader(stringReader));

        assertThat(request).isNotNull()
                .hasFieldOrPropertyWithValue("endDate", LocalDate.parse("2020-01-26"))
                .hasFieldOrPropertyWithValue("startDate", LocalDate.parse("2020-01-14"));
    }

    @Test
    void shouldThrowExceptionWhenDateIsNotInISOFormat() {
        String data = "{\"startDate\": \"2020/01/14\", \"endDate\": \"2020-01-26\"}";
        StringReader stringReader = new StringReader(data);
        ObjectMapper mapper = new ObjectMapper();
        InputParser parser = new InputParser(mapper);
        Throwable t = catchThrowable(() -> parser.parseStat(new BufferedReader(stringReader)));

        assertThat(t).isNotNull()
                .isInstanceOf(InvalidInputException.class)
                .hasMessageContaining("ISO");
    }

    @Test
    void shouldThrowExceptionWhenDateIsNotPresent() {
        String data = "{\"startingDate\": \"2020/01/14\", \"endDate\": \"2020-01-26\"}";
        StringReader stringReader = new StringReader(data);
        ObjectMapper mapper = new ObjectMapper();
        InputParser parser = new InputParser(mapper);
        Throwable t = catchThrowable(() -> parser.parseStat(new BufferedReader(stringReader)));

        assertThat(t).isNotNull()
                .isInstanceOf(InvalidInputException.class)
                .hasMessageContaining("Root is not an object, or it does not have startDate and endDate specified");
    }

    @Test
    void shouldParseCriteria(){
        String data = "{\"criterias\":[" +
                "{\"lastName\": \"Иванов\"}," +
                "{\"productName\": \"Минеральная вода\", \"minTimes\": 5}, " +
                "{\"minExpenses\": 112, \"maxExpenses\": 4000}, " +
                "{\"badCustomers\": 3}," +
                "{\"badCustomers\": 4}" +
                "]}";

        StringReader stringReader = new StringReader(data);
        ObjectMapper mapper = new ObjectMapper();
        InputParser parser = new InputParser(mapper);
        CustomerSearchRequest request = parser.parseSearch(stringReader);

        assertThat(request).isNotNull();
        assertThat(request.getCriterias())
                .hasSize(5)
                .satisfiesExactly(
                        cr -> assertThat(cr).isInstanceOf(CustomerWithLastName.class),
                        cr -> assertThat(cr).isInstanceOf(CustomerOfSpecificProduct.class),
                        cr -> assertThat(cr).isInstanceOf(CustomerSpentTotalOfRange.class),
                        cr -> assertThat(cr).isInstanceOf(PassiveCustomer.class),
                        cr -> assertThat(cr).isInstanceOf(PassiveCustomer.class)
                        );
    }

    @Test
    void shouldThrowExceptionWhenUnknownCriteriaGiven(){

    }
}