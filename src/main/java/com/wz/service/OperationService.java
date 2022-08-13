package com.wz.service;

import com.wz.criteria.CriterionResult;
import com.wz.criteria.SearchCriterion;
import com.wz.criteria.customer.CustomerSearchCriterion;
import com.wz.dao.CustomerDao;
import com.wz.dao.StatisticsDao;
import com.wz.domain.Customer;
import com.wz.dto.CriteriaReport;
import com.wz.dto.CustomerSearchRequest;
import com.wz.dto.StatRequest;
import com.wz.dto.StatisticsReport;
import com.wz.exception.InvalidInputException;
import com.wz.statistic.CustomerStatistic;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
public class OperationService {
    private final CustomerDao customerDao;
    private final StatisticsDao statisticsDao;

    public OperationService(CustomerDao customerDao,
                            StatisticsDao statisticsDao) {
        this.customerDao = customerDao;
        this.statisticsDao = statisticsDao;

    }

    public CriteriaReport search(CustomerSearchRequest customerSearchRequest) {
        List<CustomerSearchCriterion> criterias = customerSearchRequest.getCriterias();
        List<CriterionResult<Customer>> criteriaResults = new ArrayList<>();
        for (CustomerSearchCriterion criterion : criterias) {
            criteriaResults.add(new CriterionResult<>(criterion, criterion.search(customerDao)));
        }
        return new CriteriaReport("search", criteriaResults);
    }

    public StatisticsReport stat(StatRequest request) {
        LocalDate fromDateIncluded = request.getStartDate();
        LocalDate untilDateIncluded = request.getEndDate();
        if (fromDateIncluded.isAfter(untilDateIncluded))
            throw new InvalidInputException("Start date cannot be later than end date");
        List<LocalDate> excludedDates = new ArrayList<>();
        LocalDate nextDay = fromDateIncluded;
        long daysBetweenDates = DAYS.between(fromDateIncluded, untilDateIncluded) + 1;
        while (!nextDay.isAfter(untilDateIncluded)) {
            if (nextDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                    nextDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                daysBetweenDates--;
                excludedDates.add(nextDay);
            }
            nextDay = nextDay.plusDays(1);
        }
        List<CustomerStatistic> customerStatisticsSorted =
                statisticsDao.gatherPurchaseStatisticBetweenDates(fromDateIncluded, untilDateIncluded, excludedDates)
                        .stream()
                        .sorted(Comparator.comparing(CustomerStatistic::getSpentTotal).reversed())
                        .collect(Collectors.toList());

        long sum = customerStatisticsSorted.stream().mapToLong(CustomerStatistic::getSpentTotal).sum();
        int customersNum = customerStatisticsSorted.size();

        return new StatisticsReport("stat",
                daysBetweenDates,
                customerStatisticsSorted,
                new BigDecimal(sum),
                customersNum != 0 ? new BigDecimal(sum).divide(new BigDecimal(customersNum), 2, RoundingMode.CEILING) : BigDecimal.ZERO);
    }
}
