package com.wz.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonPropertyOrder({"name", "purchases"})
public class CustomerStatistic {
    @JsonProperty("name")
    @EqualsAndHashCode.Include
    private String customerFullName;
    List<ProductSummary> purchases = new ArrayList<>();
    @JsonProperty("totalExpenses")
    private long spentTotal;

    public CustomerStatistic(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public void addProductSummary(ProductSummary ps){
        purchases.add(ps);
    }
}
