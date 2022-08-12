package com.wz.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Purchase {
    private Long id;
    @EqualsAndHashCode.Include
    private Customer customer;
    @EqualsAndHashCode.Include
    private Product product;
    private LocalDate purchasedOn;
}
