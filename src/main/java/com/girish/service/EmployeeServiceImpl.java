package com.girish.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.girish.controller.EmployeeController;
import com.girish.dto.AddressRecord;
import com.girish.model.Employee;
import com.girish.repository.EmployeeRepository;




@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	private final RestTemplate restTemplate;
	
	public EmployeeServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
	
	
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

	public AddressRecord getEmployeeAddress(Integer id){		
		AddressRecord address = restTemplate.getForObject("http://localhost:8003/addressservice/addressbyid/{id}", AddressRecord.class, id);
        return address;
	}
	
	public List<AddressRecord> getAllEmployeeAddress() {		
		ResponseEntity<AddressRecord[]> addressList = restTemplate.getForEntity("http://localhost:8003/addressservice/getalladdress", AddressRecord[].class);
		return Arrays.asList(addressList.getBody());				
    }


	@Override
	public AddressRecord addEmployeeAddress(AddressRecord address) {		
		AddressRecord result = restTemplate.postForObject("http://localhost:8003/addressservice/insertaddress", address, AddressRecord.class);	
		return result;
	}
	
	@Override
	public void updateEmployeeAddress(AddressRecord address) {				
		restTemplate.put("http://localhost:8003/addressservice/updateaddress", address, AddressRecord.class);
		
	}

	@Override
	public void deleteEmployeeAddress(Integer id) {
		restTemplate.delete("http://localhost:8003/addressservice/delete/{id}", id);
	}
	
	
}