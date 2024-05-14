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
import com.girish.service.EmployeeService;

@CrossOrigin
@RestController
public class EmployeeController {	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	
	@Autowired
	EmployeeService empService;
	
	@GetMapping("/employeebyid/{id}")
	public ResponseEntity<Employee> getByEmployeeId(@PathVariable("id") int id) {
		logger.info("EmployeeController - getByEmployeeId-id:"+id);	
		Employee emp = empService.getByEmployeeId(id).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+id+" not found with the given ID."));
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}
	
	@GetMapping("/getallemployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> emps = empService.getAllEmployees();		
		if (emps.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		return new ResponseEntity<>(emps, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
		logger.info("EmployeeController - deleteEmployee-id:"+id);	
		Employee emp = empService.getByEmployeeId(id).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+id+" not found with the given ID."));	
		empService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

	@PostMapping("/insertemployee")
	public ResponseEntity<Employee> insertEmployee(@RequestBody Employee employee) {
		Employee emp=empService.addEmployee(employee);
		return new ResponseEntity<>(emp, HttpStatus.CREATED);
		
	}

	@PutMapping("/updateemployee")
	public ResponseEntity<Employee>  updateEmployee(@RequestBody Employee employee) {	
		logger.info("EmployeeController - updateEmployee-id:"+employee.getEmployeeID());	
		empService.getByEmployeeId(employee.getEmployeeID()).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+employee.getEmployeeID()+" not found with the given ID."));	
		return new ResponseEntity<>(empService.addEmployee(employee), HttpStatus.OK);	
	}
	
	
}