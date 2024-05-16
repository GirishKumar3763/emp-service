package com.girish.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.girish.model.Employee;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Disabled
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveEmployeeTest() {
		
		Employee employee = Employee.builder()
				                    .firstName("Ramesh1")
				                    .lastName("Faddoo1")
				                    .sex("Male")
				                    .employeeType("Permanent")
				                    .build();
		empRepository.save(employee);
		Assertions.assertThat(employee.getEmployeeID()).isGreaterThan(0);
		
	}
	
	@Test
	@Order(2)
	public void getEmployeeByIdTest() {
		
		Employee employee = empRepository.findById(2).get();
		Assertions.assertThat(employee.getEmployeeID()).isEqualTo(2);
		
	}
	@Disabled
	@Test
	@Order(3)
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void getAllEmployeesTest() {
		
		List<Employee> employee = empRepository.findAll();
		Assertions.assertThat(employee.size()).isEqualTo(6);
		
	}
	
	@Test
	@Order(4)
	@Rollback(value = false)
	public void updateEmployeeTest() {
		Employee employee = empRepository.findById(2).get();
		employee.setEmployeeType("Permanent");
		Employee empUpdated = empRepository.save(employee);
		Assertions.assertThat(empUpdated.getEmployeeType()).isEqualTo("Permanent");
	}
	
	@Test
	@Order(5)
	public void findByFirstNameTest() {
		
		Employee employee = empRepository.findByFirstName("girish").get(0);
		Assertions.assertThat(employee.getEmployeeID()).isEqualTo(3);
		
	}
	
	@Disabled
	@Test
	@Order(6)
	@Rollback(value = false)
	public void deleteByEmployeeIDTest() {
		
		Employee employee = empRepository.findById(5).get();
		empRepository.delete(employee);	
	}

}
