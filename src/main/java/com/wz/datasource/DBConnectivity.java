package com.wz.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DBConnectivity {
    public DataSource getDatasource(String dbUrl, String dbUser, String dbPassword) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        DataSource ds = new HikariDataSource(config);
        return ds;
    }
}
