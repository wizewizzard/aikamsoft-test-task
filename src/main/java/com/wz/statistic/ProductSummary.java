package com.wz.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSummary {
    @JsonProperty("name")
    private String productName;
    @JsonProperty("expenses")
    private long spentOnProduct;
}
