package com.wz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.criteria.CriterionResult;
import com.wz.criteria.customer.*;
import com.wz.dao.CustomerDao;
import com.wz.dao.CustomerRepository;
import com.wz.dao.StatisticsDao;
import com.wz.dao.StatisticsRepository;
import com.wz.datasource.DBConnectivity;
import com.wz.domain.Customer;
import com.wz.dto.CriteriaReport;
import com.wz.dto.CustomerSearchRequest;
import com.wz.dto.StatRequest;
import com.wz.dto.StatisticsReport;
import com.wz.exception.InvalidInputException;
import com.wz.statistic.CustomerStatistic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OperationServiceTest {
    static DataSource dataSource;
    private OperationService operationService;

    @BeforeEach
    public void beforeEach() {
        CustomerDao customerDao = new CustomerRepository(dataSource);
        StatisticsDao statisticsDao = new StatisticsRepository(dataSource);
        operationService = new OperationService(customerDao, statisticsDao);
    }

    @Test
    void shouldMakeListOfCriteriaResultsOutOfMultipleCriteria() throws JsonProcessingException {
        List<CustomerSearchCriterion> criteria = new ArrayList<>();
        criteria.add(new PassiveCustomer(3));
        criteria.add(new CustomerWithLastName("Brook"));
        criteria.add(new CustomerWithLastName("Chen"));
        criteria.add(new CustomerSpentTotalOfRange(1000, 3000));
        criteria.add(new CustomerOfSpecificProduct("product 1", 3));
        criteria.add(new PassiveCustomer(5));

        CustomerSearchRequest request = new CustomerSearchRequest(criteria);

        CriteriaReport report = operationService.search(request);
        List<CriterionResult<Customer>> results = report.getResults();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(results));
        assertThat(results).hasSize(criteria.size());
        assertThat(results).satisfiesExactly(
                r -> {
                    assertThat(r.getCriterion()).isEqualTo(criteria.get(0));
                    assertThat(r.getResults())
                            .hasSize(3)
                            .satisfiesExactly(
                                    c -> assertThat(c.getId()).isEqualTo(5L),
                                    c -> assertThat(c.getId()).isEqualTo(3L),
                                    c -> assertThat(c.getId()).isEqualTo(2L)
                            );
                },
                r -> {
                    assertThat(r.getCriterion()).isEqualTo(criteria.get(1));
                    assertThat(r.getResults())
                            .hasSize(2)
                            .satisfiesExactlyInAnyOrder(
                                    c -> assertThat(c.getId()).isEqualTo(1L),
                                    c -> assertThat(c.getId()).isEqualTo((4L))
                            );
                },
                r -> {
                    assertThat(r.getCriterion()).isEqualTo(criteria.get(2));
                    assertThat(r.getResults()).isEmpty();
                },
                r -> {
                    assertThat(r.getCriterion()).isEqualTo(criteria.get(3));
                    assertThat(r.getResults())
                            .hasSize(1)
                            .satisfiesExactly(c -> assertThat(c.getId()).isEqualTo(1L));
                },
                r -> {
                    assertThat(r.getCriterion()).isEqualTo(criteria.get(4));
                    assertThat(r.getResults())
                            .hasSize(2)
                            .satisfiesExactlyInAnyOrder(
                                    c -> assertThat(c.getId()).isEqualTo(1L),
                                    c -> assertThat(c.getId()).isEqualTo(4L)
                            );
                },
                r -> {
                    assertThat(r.getCriterion()).isEqualTo(criteria.get(5));
                    assertThat(r.getResults())
                            .hasSize(5)
                            .satisfiesExactly(
                                    c -> assertThat(c.getId()).isEqualTo(5L),
                                    c -> assertThat(c.getId()).isEqualTo(3L),
                                    c -> assertThat(c.getId()).isEqualTo(2L),
                                    c -> assertThat(c.getId()).isEqualTo(4L),
                                    c -> assertThat(c.getId()).isEqualTo(1L)
                            );
                }
        );
    }

    @Test
    public void shouldCorrectlyCalculateTotalDays() {
        StatisticsReport report1 =
                operationService.stat(new StatRequest(LocalDate.parse("2022-08-01"), LocalDate.parse("2022-08-06")));
        StatisticsReport report2 =
                operationService.stat(new StatRequest(LocalDate.parse("2022-08-06"), LocalDate.parse("2022-08-06")));
        StatisticsReport report3 =
                operationService.stat(new StatRequest(LocalDate.parse("2022-07-01"), LocalDate.parse("2022-08-06")));
        StatisticsReport report4 =
                operationService.stat(new StatRequest(LocalDate.parse("2022-08-05"), LocalDate.parse("2022-08-05")));
        StatisticsReport report5 =
                operationService.stat(new StatRequest(LocalDate.parse("2016-02-20"), LocalDate.parse("2016-03-02")));

        assertThat(report1.getTotalDays()).isEqualTo(5);
        assertThat(report2.getTotalDays()).isEqualTo(0);
        assertThat(report3.getTotalDays()).isEqualTo(26);
        assertThat(report4.getTotalDays()).isEqualTo(1);
        assertThat(report5.getTotalDays()).isEqualTo(8);
    }

    @Test
    public void shouldThrowExceptionIfDatesOverlap() {
        Throwable t1 = catchThrowable(
                () -> operationService.stat(new StatRequest(LocalDate.parse("2022-08-07"),
                        LocalDate.parse("2022-08-06"))));

        Throwable t2 = catchThrowable(
                () -> operationService.stat(new StatRequest(LocalDate.parse("2022-08-06"),
                        LocalDate.parse("2022-08-06"))));

        assertThat(t1)
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Start date cannot be later than end date");

        assertThat(t2).isNull();
    }

    @Test
    public void testReportConsistency() {
        StatisticsReport report1 =
                operationService.stat(new StatRequest(LocalDate.parse("2022-08-01"), LocalDate.parse("2022-08-06")));
        StatisticsReport report2 =
                operationService.stat(new StatRequest(LocalDate.parse("2022-08-06"), LocalDate.parse("2022-08-06")));

        assertThat(report1.getCustomers())
                .hasSize(4)
                .isSortedAccordingTo(Comparator.comparing(CustomerStatistic::getSpentTotal).reversed());
        assertThat(report1.getTotalDays()).isEqualTo(5);
        assertThat(report1.getTotalExpenses()).isEqualTo(new BigDecimal(2690L));
        assertThat(report1.getAvgExpenses()).isEqualTo(new BigDecimal("672.50"));

        assertThat(report2.getCustomers())
                .hasSize(0)
                .isSortedAccordingTo(Comparator.comparing(CustomerStatistic::getSpentTotal).reversed());
        assertThat(report2.getTotalDays()).isEqualTo(0);
        assertThat(report2.getTotalExpenses()).isEqualTo(BigDecimal.ZERO);
        assertThat(report2.getAvgExpenses()).isEqualTo(BigDecimal.ZERO);
    }

    @BeforeAll
    public static void setUp() throws SQLException {
        DBConnectivity dbConnectivity = new DBConnectivity();
        dataSource = dbConnectivity.getDatasource("jdbc:h2:mem:testdb", "sa", "sa");
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement createCustomerTable = connection.prepareStatement(" create table customer (\n" +
                    "\tid INT PRIMARY KEY,\n" +
                    "\tfirst_name VARCHAR(64),\n" +
                    "\tlast_name VARCHAR(64)\n" +
                    ")");
            createCustomerTable.executeUpdate();
            PreparedStatement createProductTable = connection.prepareStatement("create table product (\n" +
                    "\tid INT PRIMARY KEY,\n" +
                    "\tname VARCHAR(64),\n" +
                    "\tprice INT\n" +
                    ");");
            createProductTable.executeUpdate();
            PreparedStatement createPurchaseTable = connection.prepareStatement("create table purchase (\n" +
                    "\tid INT PRIMARY KEY,\n" +
                    "\tcustomer_id INT,\n" +
                    "\tproduct_id INT,\n" +
                    "\tdate DATE\n" +
                    ")");
            createPurchaseTable.executeUpdate();

            PreparedStatement insertCustomers = connection.prepareStatement("INSERT INTO customer (id, first_name, last_name) VALUES "
                    + "(1, 'Alice', 'Brook'),"
                    + "(2, 'cl2_fn', 'cl2_ln'),"
                    + "(3, 'cl3_fn', 'cl3_ln'),"
                    + "(4, 'John', 'Brook'),"
                    + "(5, 'cl5_fn', 'cl5_ln')"
            );
            insertCustomers.executeUpdate();

            PreparedStatement insertProducts = connection.prepareStatement("INSERT INTO product (id, name, price) VALUES "
                    + "(1, 'product 1', 90),"
                    + "(2, 'product 2', 400),"
                    + "(3, 'product 3', 800),"
                    + "(4, 'product 4', 950)"
            );
            insertProducts.executeUpdate();
            //5 -0 3 - 1 2 - 2 4 - 3 1 - 5
            PreparedStatement insertPurchases =
                    connection.prepareStatement("INSERT INTO purchase (id, customer_id, product_id, date) VALUES "
                            + "(1, 1, 3, '2022-08-01'),"
                            + "(2, 1, 1, '2022-08-02'),"
                            + "(3, 1, 2, '2022-08-04'),"
                            + "(4, 2, 1, '2022-08-01'),"
                            + "(5, 3, 4, '2022-08-04'),"
                            + "(6, 4, 1, '2022-08-04'),"
                            + "(7, 2, 1, '2022-08-06'),"
                            + "(8, 1, 1, '2022-08-03'),"
                            + "(9, 1, 1, '2022-08-04'),"
                            + "(10, 4, 1, '2022-08-01'),"
                            + "(11, 4, 1, '2022-08-06')"
                    );
            insertPurchases.executeUpdate();
        }
    }

}