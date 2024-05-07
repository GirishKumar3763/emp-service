package com.girish.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.girish.exception.EmployeeNotFoundException;
import com.girish.model.Employee;
import com.girish.repository.EmployeeRepository;

@CrossOrigin
@RestController
public class EmployeeController {	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping("/employeebyid/{id}")
	public ResponseEntity<Employee> getByEmployeeId(@PathVariable("id") int id) {
		logger.info("EmployeeController - getByEmployeeId-id:"+id);	
		Employee emp = empRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+id+" not found with the given ID."));	
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}
	
	@GetMapping("/getallemployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> emps = empRepo.findAll();
		if (emps.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		return new ResponseEntity<>(emps, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
		logger.info("EmployeeController - deleteEmployee-id:"+id);	
		Employee emp = empRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+id+" not found with the given ID."));	
		empRepo.delete(emp);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

	@PostMapping("/insertemployee")
	public ResponseEntity<Employee> insertEmployee(@RequestBody Employee employee) {
		Employee emp=empRepo.save(employee);
		return new ResponseEntity<>(emp, HttpStatus.CREATED);
		
	}

	@PutMapping("/updateemployee/{id}")
	public ResponseEntity<Employee>  updateEmployee(@PathVariable("id") int id) {	
		logger.info("EmployeeController - updateEmployee-id:"+id);	
		Employee emp = empRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+id+" not found with the given ID."));	
		emp.setFirstName("ganesh");
		emp.setLastName("kanna");
		emp.setSex("Female");
		emp.setEmployeeType("Contract");				
		return new ResponseEntity<>(empRepo.save(emp), HttpStatus.OK);	
	}
	
	
}