package com.wz.dto;

import com.wz.statistic.CustomerStatistic;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class StatisticsReport {
    private final String type;
    private final long totalDays;
    private final List<CustomerStatistic> customers;
    private final BigDecimal totalExpenses;
    private final BigDecimal avgExpenses;
}
