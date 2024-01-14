package com.inn.customerMgmt.Service;

import java.util.List;

import com.inn.customerMgmt.entity.Customer;

public interface ICustomerMgmtService {
	
	Customer getCustomerById(Long id);

	void createCustomer(Customer customer);

	List<Customer> getCustomers();

	void updateCustomerById(Long id, Customer customer);

	void deleteCustomerById(Long id);

}
