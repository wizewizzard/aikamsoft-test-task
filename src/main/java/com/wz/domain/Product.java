package com.wz.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Product {
    private Long id;
    @EqualsAndHashCode.Include
    private String name;
}
