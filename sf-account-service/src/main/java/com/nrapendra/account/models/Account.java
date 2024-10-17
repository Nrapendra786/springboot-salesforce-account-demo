package com.nrapendra.account.models;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private String name;
    private String accountNumber;
    private String phoneNumber;
    private String industry;
    private String billingCity;
    private String billingCountry;
}
