package com.ecommerce.customer.controller;

import com.ecommerce.customer.model.Customer;
import com.ecommerce.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<Customer> findAll(){
        return customerService.findAll();
    }

    @PostMapping
    public Mono<Customer> save(@Valid @RequestBody Customer customer){
        return customerService.save(customer);
    }

    @GetMapping("/{id}")
    public Mono<Customer> findCustomerById(@PathVariable String id){
        return customerService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerById(@PathVariable String id){
        customerService.deleteCustomerById(id);
    }
}
