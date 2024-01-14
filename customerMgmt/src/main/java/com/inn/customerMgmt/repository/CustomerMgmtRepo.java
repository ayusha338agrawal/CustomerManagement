package com.inn.customerMgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.customerMgmt.entity.Customer;

@Repository
public interface CustomerMgmtRepo extends JpaRepository<Customer, Long>{

}
