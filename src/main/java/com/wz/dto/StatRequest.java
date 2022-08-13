package com.wz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class StatRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
