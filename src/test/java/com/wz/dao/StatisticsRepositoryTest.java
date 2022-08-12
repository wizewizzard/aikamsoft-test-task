package com.wz.dao;

import com.wz.datasource.DBConnectivity;
import com.wz.domain.Customer;
import com.wz.statistic.CustomerStatistic;
import com.wz.statistic.ProductSummary;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class StatisticsRepositoryTest {

    static DataSource dataSource;

    @Test
    public void testPlaceholders() {
        String placeHolders = String.join(",", Collections.nCopies(5, "?"));
        System.out.println(placeHolders);
        System.out.println(placeHolders.isEmpty());
    }

    @Test
    void shouldGatherPurchaseStatisticBetweenDates() {
        StatisticsRepository statisticsRepository = new StatisticsRepository(dataSource);

        List<CustomerStatistic> statistics1 = statisticsRepository.gatherPurchaseStatisticBetweenDates(
                LocalDate.parse("2022-07-23"),
                LocalDate.parse("2022-07-28"),
                new ArrayList<>());

        List<CustomerStatistic> statistics2 =
                statisticsRepository.gatherPurchaseStatisticBetweenDates(
                        LocalDate.parse("2022-07-23"),
                        LocalDate.parse("2022-07-23"),
                        new ArrayList<>());


        List<CustomerStatistic> statistics3 =
                statisticsRepository.gatherPurchaseStatisticBetweenDates(
                        LocalDate.parse("2022-07-23"),
                        LocalDate.parse("2022-08-10"),
                        new ArrayList<>());

        Condition<Map.Entry<Customer, List<ProductSummary>>> hasNoPurchases =
                new Condition<>(e -> e.getValue().isEmpty(), "no purchases");

        Condition<Map.Entry<Customer, List<ProductSummary>>> aliceBrookCondition =
                new Condition<>(e -> e.getKey().getId().equals(1L) && e.getValue().size() == 3,
                        "Alice Brook has all purchases"
                );

        assertThat(statistics1)
                .hasSize(2)
                .anyMatch(st -> st.getCustomerFullName().equals("Alice Brook") && st.getSpentTotal() == 800)
                .anyMatch(st -> st.getCustomerFullName().equals("John Brook") && st.getSpentTotal() == 90);

        assertThat(statistics2)
                .isEmpty();

        assertThat(statistics3)
                .hasSize(4)
                .anyMatch(st -> st.getCustomerFullName().equals("Alice Brook") && st.getSpentTotal() == 1470)
                .anyMatch(st -> st.getCustomerFullName().equals("John Brook") && st.getSpentTotal() == 270)
                .anyMatch(st -> st.getCustomerFullName().equals("cl3_fn cl3_ln") && st.getSpentTotal() == 950)
                .anyMatch(st -> st.getCustomerFullName().equals("cl2_fn cl2_ln") && st.getSpentTotal() == 180)
        ;
    }

    @Test
    public void shouldExcludeGivenDates() {
        StatisticsRepository statisticsRepository = new StatisticsRepository(dataSource);

        List<CustomerStatistic> customerStatistics =
                statisticsRepository.gatherPurchaseStatisticBetweenDates(
                        LocalDate.parse("2022-07-23"),
                        LocalDate.parse("2022-08-10"),
                        Arrays.asList(LocalDate.parse("2022-08-02"), LocalDate.parse("2022-07-31"))

                );

        assertThat(customerStatistics)
                .anyMatch(st ->
                    st.getCustomerFullName().equals("Alice Brook") && st.getPurchases()
                            .stream().anyMatch(p -> p.getProductName().equals("product 1") && p.getSpentOnProduct() == 90)
                );

        customerStatistics =
                statisticsRepository.gatherPurchaseStatisticBetweenDates(
                        LocalDate.parse("2022-07-23"),
                        LocalDate.parse("2022-08-10"),
                        Arrays.asList(LocalDate.parse("2022-08-02"),
                                LocalDate.parse("2022-08-04"),
                                LocalDate.parse("2022-07-31"))

                );

        assertThat(customerStatistics)
                .anyMatch(st ->
                        st.getCustomerFullName().equals("Alice Brook") && st.getPurchases()
                                .stream().noneMatch(p -> p.getProductName().equals("product 1"))
                );
    }

    @BeforeAll
    public static void beforeAll() throws SQLException {
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
                            + "(1, 1, 3, '2022-07-25'),"
                            + "(2, 1, 1, '2022-08-02'),"
                            + "(3, 1, 2, '2022-08-04'),"
                            + "(4, 2, 1, '2022-08-01'),"
                            + "(5, 3, 4, '2022-08-04'),"
                            + "(6, 4, 1, '2022-07-28'),"
                            + "(7, 2, 1, '2022-08-06'),"
                            + "(8, 1, 1, '2022-07-31'),"
                            + "(9, 1, 1, '2022-08-04'),"
                            + "(10, 4, 1, '2022-07-30'),"
                            + "(11, 4, 1, '2022-08-06')"
                    );
            insertPurchases.executeUpdate();
        }
    }
}