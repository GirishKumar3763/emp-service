package com.girish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.girish.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	public List<Employee> findByFirstName(String firstName);
	//public List<Employee> findByLastNameOrderByAgeDesc(String lastName);
	

}