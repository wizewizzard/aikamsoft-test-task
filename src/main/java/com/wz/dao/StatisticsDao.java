package com.wz.dao;

import com.wz.domain.Customer;
import com.wz.statistic.CustomerStatistic;
import com.wz.statistic.ProductSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatisticsDao {
    /**
     * @param fromDateIncluded  the date from which statistics will be queried
     * @param untilDateIncluded the date that will be included in selection until which the statistics will be queried
     * @param excludedDates     List of dates that must be excluded
     * @return List of stats for every buyer that was active during given period. Contains full name, ordered DESC
     * product summaries and total sum spent on the products
     */
    List<CustomerStatistic> gatherPurchaseStatisticBetweenDates(LocalDate fromDateIncluded,
                                                               LocalDate untilDateIncluded,
                                                               List<LocalDate> excludedDates);
}
