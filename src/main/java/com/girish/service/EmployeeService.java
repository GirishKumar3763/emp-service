package com.girish.service;

import java.util.List;
import java.util.Optional;

import com.girish.model.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	public List<Employee> findByEmployeeName(String name);
	public Optional<Employee> getByEmployeeId(Integer id);
	public void deleteEmployee(Integer employeeId);
	public Employee addEmployee(Employee employee);
	public Employee updateEmployee(Employee employee);
	//public List<Employee> findByLastNameOrderByAgeDesc(String lastName);

}