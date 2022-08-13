package com.wz.dao;

import com.wz.domain.Customer;
import com.wz.exception.DataAccessException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomerRepository implements CustomerDao{
    private final DataSource dataSource;

    public CustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Customer> findCustomersByLastName(String lastName){
        try(Connection connection = dataSource.getConnection()){
            String query = "SELECT cu.id, cu.first_name, cu.last_name FROM customer cu WHERE cu.last_name = ?";
            PreparedStatement prStmnt = connection.prepareStatement(query);
            prStmnt.setString(1, lastName);
            ResultSet rs = prStmnt.executeQuery();
            List<Customer> resultCustomers = new ArrayList<>();

            while(rs.next()){
                resultCustomers.add(mapCustomer(rs));
            }
            return resultCustomers;
        }
        catch (SQLException exception){
            log.error("SQL error when searching customers by last name", exception);
            throw new DataAccessException("SQL error when searching customers by last name");
        }
    }

    @Override
    public List<Customer> findCustomersByProductTheyBoughtAtLeastNTimes(String productName, int times) {
        try(Connection connection = dataSource.getConnection()){
            String query = "select cu.id, cu.first_name, cu.last_name, count(*)\n" +
                    "from customer cu\n" +
                    "inner join purchase pu on cu.id = pu.customer_id\n" +
                    "inner join product pr on pr.id = pu.product_id\n" +
                    "where pr.name= ?\n" +
                    "group by cu.id, cu.first_name, cu.last_name\n" +
                    "having count(*) >= ?;";

            PreparedStatement prStmnt = connection.prepareStatement(query);
            prStmnt.setString(1, productName);
            prStmnt.setInt(2, times);
            ResultSet rs = prStmnt.executeQuery();
            List<Customer> resultCustomers = new ArrayList<>();
            while(rs.next()){
                resultCustomers.add(mapCustomer(rs));
            }
            return resultCustomers;
        }
        catch (SQLException exception){
            log.error("SQL error when searching for customers who bought product at least N times", exception);
            throw new DataAccessException("SQL error when searching for customers who bought product at least N times");
        }
    }

    @Override
    public List<Customer> findCustomersInRangeOfPurchaseSum(int minSum, int maxSum) {
        try(Connection connection = dataSource.getConnection()){
            String query = "select cu.id, cu.first_name, cu.last_name\n" +
                    "from customer cu\n" +
                    "left join purchase pu on cu.id = pu.customer_id\n" +
                    "left join product pr on pr.id = pu.product_id\n" +
                    "group by cu.id, cu.first_name, cu.last_name\n" +
                    "having COALESCE(SUM(pr.price), 0) BETWEEN ? and ?";

            PreparedStatement prStmnt = connection.prepareStatement(query);
            prStmnt.setInt(1, minSum);
            prStmnt.setInt(2, maxSum);
            ResultSet rs = prStmnt.executeQuery();
            List<Customer> resultCustomers = new ArrayList<>();
            while(rs.next()){
                resultCustomers.add(mapCustomer(rs));
            }
            return resultCustomers;
        }
        catch (SQLException exception){
            log.error("SQL error when searching for customers who spent total sum in specified range", exception);
            throw new DataAccessException("SQL error when searching for customers who spent total sum in specified range");
        }
    }

    @Override
    public List<Customer> findPassiveCustomers(int count) {
        try(Connection connection = dataSource.getConnection()){
            String query = "select cu.id, cu.first_name, cu.last_name\n" +
                    "from customer cu\n" +
                    "left join (select pu.customer_id as customer_id, count(*) as purchase_count\n" +
                    "from purchase pu\n" +
                    "group by pu.customer_id) subq \n" +
                    "on subq.customer_id = cu.id\n" +
                    "order by COALESCE(purchase_count, 0) asc\n" +
                    "limit ?";

            PreparedStatement prStmnt = connection.prepareStatement(query);
            prStmnt.setInt(1, count);
            ResultSet rs = prStmnt.executeQuery();
            List<Customer> resultCustomers = new ArrayList<>();
            while(rs.next()){
                resultCustomers.add(mapCustomer(rs));
            }
            return resultCustomers;
        }
        catch (SQLException exception){
            log.error("SQL error when searching for passive customers", exception);
            throw new DataAccessException("SQL error when searching for passive customers");
        }
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        return new Customer(rs.getLong(1), rs.getString(2), rs.getString(3));
    }
}
