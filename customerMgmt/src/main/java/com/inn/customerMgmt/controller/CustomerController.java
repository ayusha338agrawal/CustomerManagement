package com.inn.customerMgmt.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.inn.customerMgmt.Service.ICustomerMgmtService;
import com.inn.customerMgmt.entity.Customer;

@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	ICustomerMgmtService customerMgmtService;

	@GetMapping("/customers/{id}")
	public Customer getCustomerById(@PathVariable("id") Long id) {
		return customerMgmtService.getCustomerById(id) ;
	}
	
	@PostMapping("/customers")
	public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
	    customerMgmtService.createCustomer(customer);
	    URI location = ServletUriComponentsBuilder
	            .fromCurrentRequest()
	            .path("/{id}")
	            .buildAndExpand(customer.getId())
	            .toUri();
	    return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerMgmtService.getCustomers();
	}
	
	@PostMapping("/customers/{id}")
	public void updateCustomerById(@PathVariable("id") Long id, @RequestBody Customer customer) {
		customerMgmtService.updateCustomerById(id, customer);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomerById(@PathVariable("id") Long id) {
		customerMgmtService.deleteCustomerById(id);
	}
}
