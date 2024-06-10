package com.girish.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.girish.dto.AddressRecord;
import com.girish.model.Employee;
import com.girish.repository.EmployeeRepository;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;




@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ObservationRegistry observationRegistry;
	
	private final RestTemplate restTemplate;
	
	public EmployeeServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
	
	
	public List<Employee> getAllEmployees() {
		return Observation.createNotStarted("getAllEmployees", observationRegistry)
				          .observe(()->(List<Employee>) employeeRepository.findAll());
				
	}

	
	public List<Employee> findByEmployeeName(String name) {
			
		return Observation.createNotStarted("findByEmployeeName", observationRegistry)
		          .observe(()->(List<Employee>)employeeRepository.findByFirstName(name));
		
	}

	public Optional<Employee> getByEmployeeId(Integer id){
		return Observation.createNotStarted("getByEmployeeId", observationRegistry)
		          .observe(()->employeeRepository.findById(id));
	}

	
	public void deleteEmployee(Integer personId) {
		
		Observation.createNotStarted("deleteEmployee", observationRegistry)
                   .observe(()->employeeRepository.deleteById(personId));
	}

	
	public Employee addEmployee(Employee employee) {
		
		return Observation.createNotStarted("addEmployee", observationRegistry)
				          .observe(()->employeeRepository.save(employee));
	}	
	
	public Employee updateEmployee(Employee employee) {
		
		return Observation.createNotStarted("updateEmployee", observationRegistry)
		          .observe(()->employeeRepository.save(employee));
			
	}

	public AddressRecord getEmployeeAddress(Integer id){		
		AddressRecord address = restTemplate.getForObject("http://localhost:8003/address-service/addressbyid/{id}", AddressRecord.class, id);
        return address;
	}
	
	public List<AddressRecord> getAllEmployeeAddress() {		
		ResponseEntity<AddressRecord[]> addressList = restTemplate.getForEntity("http://localhost:8003/address-service/getalladdress", AddressRecord[].class);
		return Arrays.asList(addressList.getBody());				
    }


	@Override
	public AddressRecord addEmployeeAddress(AddressRecord address) {		
		AddressRecord result = restTemplate.postForObject("http://localhost:8003/address-service/insertaddress", address, AddressRecord.class);	
		return result;
	}
	
	@Override
	public void updateEmployeeAddress(AddressRecord address) {				
		restTemplate.put("http://localhost:8003/address-service/updateaddress", address, AddressRecord.class);
		
	}

	@Override
	public void deleteEmployeeAddress(Integer id) {
		restTemplate.delete("http://localhost:8003/address-service/delete/{id}", id);
	}
	
	
}