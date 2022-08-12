package com.wz.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Customer {
    private Long id;
    @EqualsAndHashCode.Include
    private String firstName;
    @EqualsAndHashCode.Include
    private String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
