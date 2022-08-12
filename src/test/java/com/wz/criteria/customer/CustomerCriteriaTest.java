package com.wz.criteria.customer;

import com.wz.dao.CustomerDao;
import com.wz.dao.CustomerRepository;
import com.wz.datasource.DBConnectivity;
import com.wz.domain.Customer;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerCriteriaTest {
    static DataSource dataSource;
    static CustomerDao customerDao;

    @Test
    void shouldFindCustomersByLastName() {
        CustomerSearchCriterion lastNameCriteria = new CustomerWithLastName("Brook");

        Condition<Customer> hasLastNameBrook =
                new Condition<>(c -> c.getLastName().equals("Brook"),
                        "brook"
                );

        assertThat(lastNameCriteria.search(customerDao))
                .hasSize(2)
                .are(hasLastNameBrook);
    }

    @Test
    void shouldReturnEmptyCollectionWhenLastNameIsNotPresent() {
        CustomerSearchCriterion lastNameCriteria = new CustomerWithLastName("Blah");

        assertThat(lastNameCriteria.search(customerDao)).hasSize(0);
    }

    @Test
    public void shouldFindCustomersWhoBoughtAProductAtLeastNTimes() {
        CustomerSearchCriterion purchaseNumCriteria = new CustomerOfSpecificProduct("product 1", 2);

        Collection<Customer> customers = purchaseNumCriteria.search(customerDao);

        assertThat(customers).anyMatch(c -> c.getId() == (1L));
        assertThat(customers).anyMatch(c -> c.getId() == (2L));
        assertThat(customers).anyMatch(c -> c.getId() == (4L));
    }

    @Test
    public void shouldNotFindAnyCustomers() {
        CustomerSearchCriterion purchaseNumCriteria1 = new CustomerOfSpecificProduct("product 1", 10);
        CustomerSearchCriterion purchaseNumCriteria2 = new CustomerOfSpecificProduct("product 9999", 1);

        assertThat(purchaseNumCriteria1.search(customerDao)).isNotNull().isEmpty();
        assertThat(purchaseNumCriteria2.search(customerDao)).isNotNull().isEmpty();
    }

    @Test
    public void shouldFindCustomersInRangeOfPurchaseSum() {
        CustomerSearchCriterion purchaseSummaryCriteriaForCustomersWhoBoughtNothing =
                new CustomerSpentTotalOfRange(0, 0);
        CustomerSearchCriterion purchaseSummaryCriteriaForWealthy =
                new CustomerSpentTotalOfRange(10000, 11000);
        CustomerSearchCriterion purchaseSummaryCriteria =
                new CustomerSpentTotalOfRange(100, 500);


        assertThat(purchaseSummaryCriteriaForCustomersWhoBoughtNothing.search(customerDao))
                .hasSize(1).allMatch(c -> c.getId() == 5L);
        assertThat(purchaseSummaryCriteriaForWealthy.search(customerDao))
                .isNotNull().isEmpty();
        assertThat(purchaseSummaryCriteria.search(customerDao))
                .hasSize(2);

    }

    @Test
    public void shouldFindPassiveCustomers() {
        CustomerSearchCriterion passiveCustomersCriteriaZeroCustomers =
                new PassiveCustomer(0);
        CustomerSearchCriterion passiveCustomersCriteria =
                new PassiveCustomer(4);
        ;
        CustomerSearchCriterion passiveCustomersCriteriaOver =
                new PassiveCustomer(150);
        ;

        assertThat(passiveCustomersCriteriaZeroCustomers.search(customerDao))
                .isNotNull()
                .isEmpty();
        assertThat(passiveCustomersCriteria.search(customerDao))
                .hasSize(4)
                .satisfiesExactly(c -> assertThat(c.getId()).isEqualTo(5),
                        c -> assertThat(c.getId()).isEqualTo(3),
                        c -> assertThat(c.getId()).isEqualTo(2),
                        c -> assertThat(c.getId()).isEqualTo(4));
        assertThat(passiveCustomersCriteriaOver.search(customerDao))
                .hasSize(5)
                .satisfiesExactly(c -> assertThat(c.getId()).isEqualTo(5),
                        c -> assertThat(c.getId()).isEqualTo(3),
                        c -> assertThat(c.getId()).isEqualTo(2),
                        c -> assertThat(c.getId()).isEqualTo(4),
                        c -> assertThat(c.getId()).isEqualTo(1));
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
        customerDao = new CustomerRepository(dataSource);
    }
}