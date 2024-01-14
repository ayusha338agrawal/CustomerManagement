package com.inn.customerMgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.inn.customerMgmt.Service.Impl.CustomerMgmtServiceImpl;
import com.inn.customerMgmt.entity.Customer;
import com.inn.customerMgmt.repository.CustomerMgmtRepo;

@SpringBootTest
class CustomerServiceTest {

	@Mock
	private CustomerMgmtRepo customerRepository;

	@InjectMocks
	private CustomerMgmtServiceImpl customerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllCustomers() {
		// Arrange
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setName("Ayush Agrawal");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setName("Jane Doe");

		List<Customer> customers = Arrays.asList(customer1, customer2);

		when(customerRepository.findAll()).thenReturn(customers);

		// Act
		List<Customer> result = customerService.getCustomers();

		// Assert
		assertEquals(2, result.size());
		assertEquals("Ayush Agrawal", result.get(0).getName());
		assertEquals("Jane Doe", result.get(1).getName());

		verify(customerRepository, times(1)).findAll();
	}

	@Test
	void testGetCustomerById() {
		// Arrange
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Ayush Agrawal");

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		// Act
		Customer result = customerService.getCustomerById(1L);

		// Assert
		assertEquals("Ayush Agrawal", result.getName());

		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	void testUpdateCustomer() {
		// Arrange
		Customer existingCustomer = new Customer();
		existingCustomer.setId(1L);
		existingCustomer.setName("Ayush Agrawal");

		Customer updatedCustomer = new Customer();
		updatedCustomer.setId(1L);
		updatedCustomer.setName("Jatin Agrawal");

		when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
		when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

		// Act
		customerService.updateCustomerById(1L, updatedCustomer);

		Customer result = customerService.getCustomerById(1L);
		// Assert
		assertEquals("Jatin Agrawal", result.getName());

		verify(customerRepository, times(1)).findById(1L);
		verify(customerRepository, times(1)).save(updatedCustomer);
	}

	@Test
	void testDeleteCustomer() {
		// Arrange
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Ayush Agrawal");

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		// Act
		customerService.deleteCustomerById(1L);

		// Assert
		verify(customerRepository, times(1)).findById(1L);
		verify(customerRepository, times(1)).delete(customer);
	}
}
