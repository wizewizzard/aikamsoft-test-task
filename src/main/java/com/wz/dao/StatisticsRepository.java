package com.wz.dao;

import com.wz.domain.Customer;
import com.wz.exception.DataAccessException;
import com.wz.statistic.CustomerStatistic;
import com.wz.statistic.ProductSummary;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class StatisticsRepository implements StatisticsDao {

    private final DataSource dataSource;

    public StatisticsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<CustomerStatistic> gatherPurchaseStatisticBetweenDates(LocalDate fromDateIncluded,
                                                                       LocalDate untilDateIncluded,
                                                                       List<LocalDate> excludedDates) {
        String placeHolders = String.join(",", Collections.nCopies(excludedDates.size(), "?"));

        String query = "select cu.id, cu.first_name, cu.last_name, product_name, COALESCE(product_total, 0) as product_total " +
                "from customer cu " +
                "inner join " +
                "(select pu.customer_id, pr.name as product_name, SUM(pr.price) as product_total " +
                "from purchase pu " +
                "inner join product pr on pr.id = pu.product_id " +
                "where pu.date BETWEEN ? and ? " +
                (!placeHolders.isEmpty() ? "and  pu.date not in (" + placeHolders + ") " : " ") +
                "group by pu.customer_id, pr.name) subq " +
                "on cu.id = subq.customer_id " +
                "order by product_total desc";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement prStmnt = connection.prepareStatement(query)) {
            prStmnt.setDate(1, Date.valueOf(fromDateIncluded));
            prStmnt.setDate(2, Date.valueOf(untilDateIncluded));
            if (excludedDates.size() > 0)
                for (int i = 0; i < excludedDates.size(); i++) {
                    prStmnt.setDate(3 + i, Date.valueOf(excludedDates.get(i)));
                }
            ResultSet rs = prStmnt.executeQuery();
            Map<String, CustomerStatistic> customerStatistics = new HashMap<>();
            while (rs.next()) {
                String fullName = rs.getString(2) + " " + rs.getString(3);
                customerStatistics.computeIfAbsent(fullName, k -> new CustomerStatistic(fullName));
                CustomerStatistic customerStatistic = customerStatistics.get(fullName);
                if (rs.getString(4) != null){
                    customerStatistic.addProductSummary(
                            new ProductSummary(rs.getString(4), Long.parseLong(rs.getString(5))));
                    customerStatistic.setSpentTotal(customerStatistic.getSpentTotal() + Long.parseLong(rs.getString(5)));
                }
            }
            return new ArrayList<>(customerStatistics.values());
        } catch (NumberFormatException exception) {
            log.error("Values in database are not consistent", exception);
            throw new DataAccessException("Error when reading values from database");
        } catch (SQLException exception) {
            log.error("SQL error when gathering statistics", exception);
            throw new DataAccessException("SQL error when gathering statistics");
        }
    }
}
