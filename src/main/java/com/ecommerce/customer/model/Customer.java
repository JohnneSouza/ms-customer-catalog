package com.ecommerce.customer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "customers_catalog_db")
public class Customer {

    @Id
    private String id;
    @NotEmpty(message = "name must not be empty")
    private String name;
    private String birthday;
    private Address address;
    @NotEmpty(message = "e-mail must not be empty")
    private String email;
    private Date lastLogin;
    private String phoneNumber;
}
