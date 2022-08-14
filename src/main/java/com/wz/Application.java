package com.wz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.dao.CustomerRepository;
import com.wz.dao.StatisticsRepository;
import com.wz.datasource.DBConnectivity;
import com.wz.dto.CustomerSearchRequest;
import com.wz.dto.StatRequest;
import com.wz.exception.DataAccessException;
import com.wz.exception.InvalidInputException;
import com.wz.parsing.InputParser;
import com.wz.service.OperationService;
import com.wz.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class Application {
    public static void main(String[] args) {
        String operationName = args[0];
        String inputFileName = args[1];
        String outputFileName = args[2];

        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        String dbUrl = System.getenv("DB_URL");
        boolean pretty = System.getenv("JSON_PRETTY") == null ||
                Boolean.parseBoolean(System.getenv("JSON_PRETTY"));

        log.info("Creating dependencies...");
        ObjectMapper mapper = new ObjectMapper();
        InputParser parser = new InputParser(mapper);
        DBConnectivity dbConnectivity = new DBConnectivity();
        DataSource dataSource;
        try {
            dataSource = dbConnectivity.getDatasource(dbUrl, user, password);
        } catch (Throwable exception) {
            log.error("Exception when establishing connection to the database {} for user {}", dbUrl, user, exception);
            throw new RuntimeException("Error when establishing connection to the database", exception);
        }
        OperationService operationService =
                new OperationService(new CustomerRepository(dataSource),
                        new StatisticsRepository(dataSource));

        log.info("Starting to perform operation");
        try (FileReader reader = new FileReader(inputFileName);
             FileWriter writer = new FileWriter(outputFileName);) {
            Object report;
            switch (operationName) {
                case "search": {
                    CustomerSearchRequest searchRequest = parser.parseSearch(reader);
                    report = operationService.search(searchRequest);
                    break;
                }
                case "stat": {
                    StatRequest statRequest = parser.parseStat(reader);
                    report = operationService.stat(statRequest);
                    break;
                }
                default:
                    throw new InvalidInputException("You've specified an unsupported operation");
            }
            if (pretty)
                writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(report));
            else
                writer.write(mapper.writeValueAsString(report));
            writer.flush();
            log.info("Successfully performed operation");
        } catch (InvalidInputException | DataAccessException exception) {
            log.error("Error occurred", exception);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                writer.write(mapper.writeValueAsString(new ErrorMessage("error", exception.getMessage())));
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException exception) {
            log.error("Error occurred when working with files. Check if files are available.", exception);
            throw new RuntimeException("Error occurred when working with files. Check if files are available.");
        }
    }
}
