package com.inn.customerMgmt.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.customerMgmt.Service.ICustomerMgmtService;
import com.inn.customerMgmt.entity.Customer;
import com.inn.customerMgmt.repository.CustomerMgmtRepo;

@Service
public class CustomerMgmtServiceImpl implements ICustomerMgmtService {
	
	@Autowired
	CustomerMgmtRepo customerMgmtDao;
	
	@Override
	public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerMgmtDao.findById(id);
        return customerOptional.orElse(null);
	}

	@Override
	public void createCustomer(Customer customer) {
		customerMgmtDao.save(customer);
	}
	
	@Override
	public List<Customer> getCustomers(){
		return customerMgmtDao.findAll();
	}
	
	@Override
	public void updateCustomerById(Long id, Customer customer) {
        Optional<Customer> existingCustomerOptional = customerMgmtDao.findById(id);
        existingCustomerOptional.ifPresent(existingCustomer -> {
        	if(customer.getName()!=null) {
            	existingCustomer.setName(customer.getName());
        	}
        	if(customer.getEmail()!=null) {
                existingCustomer.setEmail(customer.getEmail());
        	}
        	if(customer.getPhone()!=null) {
                existingCustomer.setPhone(customer.getPhone());
        	}
            customerMgmtDao.save(existingCustomer);
        });
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerMgmtDao.deleteById(id);
	}
}
