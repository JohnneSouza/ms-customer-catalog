package com.ecommerce.customer.service;

import com.ecommerce.customer.model.Customer;
import com.ecommerce.customer.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findById(String id){
        log.info("[{}] Finding Customer", id);
        return customerRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"))
                );
    }

    public Mono<Customer> save(Customer customer){
        log.info("Saving new Customer");
        return customerRepository.save(customer);
    }

    public Flux<Customer> findAll(){
        log.info("Returning all Customers");
        return customerRepository.findAll();
    }

    public void deleteCustomerById(String id){
        customerRepository.deleteById(id);
    }
}
