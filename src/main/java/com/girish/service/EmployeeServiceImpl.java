package com.girish.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.girish.model.Employee;
import com.girish.repository.EmployeeRepository;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

	
	public List<Employee> findByEmployeeName(String name) {
		return (List<Employee>)employeeRepository.findByFirstName(name);
	}

	public Optional<Employee> getByEmployeeId(Integer id){
	
		return employeeRepository.findById(id);
	}

	
	public void deleteEmployee(Integer personId) {
		employeeRepository.deleteById(personId);
	}

	
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee) ;
	}	
	
	public Employee updateEmployee(Employee employee) {
					
		return	employeeRepository.save(employee);
			
	}

	/*
	 * public List<Employee> findByLastNameOrderByAgeDesc(String lastName) { // TODO
	 * Auto-generated method stub return
	 * employeeRepository.findByLastNameOrderByAgeDesc(lastName); }
	 */
	
}