package com.ecommerce.customer.model;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String number;
    private String neighborhood;
    private String state;
    private String country;
    private String zipCode;

}
