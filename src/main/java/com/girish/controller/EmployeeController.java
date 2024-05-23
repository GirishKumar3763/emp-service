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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.girish.dto.AddressRecord;
import com.girish.exception.EmployeeNotFoundException;
import com.girish.model.Employee;
import com.girish.service.EmployeeService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@CrossOrigin
@RestController
public class EmployeeController {	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService empService;
	
	@GetMapping("/employeebyid/{id}")
	public ResponseEntity<Employee> getByEmployeeId(@PathVariable("id") int id) {
		logger.info("EmployeeFeignController - getByEmployeeId-id:"+id);	
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
		logger.info("EmployeeFeignController - deleteEmployee-id:"+id);	
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
		logger.info("EmployeeFeignController - updateEmployee-id:"+employee.getEmployeeID());	
		empService.getByEmployeeId(employee.getEmployeeID()).orElseThrow(() -> new EmployeeNotFoundException("Employee-"+employee.getEmployeeID()+" not found with the given ID."));	
		return new ResponseEntity<>(empService.addEmployee(employee), HttpStatus.OK);	
	}
	
	@GetMapping("/getMessage")
    @Bulkhead(name = "getMessageBulkHead", fallbackMethod = "getMessageFallBack")
    public ResponseEntity<String> getMessage(@RequestParam(value="name", defaultValue = "Hello") String name){
       return ResponseEntity.ok().body("Message from getMessage() :" +name);
    }
    public ResponseEntity<String> getMessageFallBack(RequestNotPermitted exception) {
       logger.info("Bulkhead has applied, So no further calls are getting accepted");
       return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
      .body("Too many requests : No further request will be accepted. Plese try after sometime");
    }      
    @GetMapping("/getByEmployeeByAddress/{id}")
	@RateLimiter(name = "getEmployeeRateLimit" ,fallbackMethod ="getRateLimitFallBack" )
	public ResponseEntity<AddressRecord> getByEmployeeByAddress(@PathVariable("id") int id) {
		logger.info("EmployeeFeignController - getByEmployeeByAddress-id:"+id);	
		AddressRecord emp = empService.getEmployeeAddress(id);		
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}	
	public ResponseEntity<String> getRateLimitFallBack(RequestNotPermitted exception) {
        logger.info("Rate limit has applied, So no further calls are getting accepted");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .body("Too many requests : No further request will be accepted. Please try after sometime");
    }
	
	@GetMapping("/getallemployeesaddress")
	@Retry(name = "getRetryEmployeesList",fallbackMethod = "getRetryEmployeesFallBack")
	public ResponseEntity<List<AddressRecord>> getAllEmployeesAddress() {
		List<AddressRecord> emps =  empService.getAllEmployeeAddress();
		return new ResponseEntity<>(emps, HttpStatus.OK);
		
	}
	
	public ResponseEntity<String> getRetryEmployeesFallBack(Exception e) {
		logger.info("--RESPONSE FROM FALLBACK METHOD---");
		
		return new ResponseEntity<String>("SERVER IS DOWN, PLEASE TRT AFTER SOME TIME", null);
	}
	
	@PostMapping("/insertemployeeaddress")
	@CircuitBreaker(name = "saveEmpCircuitBreaker",fallbackMethod = "saveEmpFallback")
	public ResponseEntity<AddressRecord> insertEmployeeAddress(@RequestBody AddressRecord employee) {
		AddressRecord emp=empService.addEmployeeAddress(employee);
		return new ResponseEntity<>(emp, HttpStatus.CREATED);
		
	}
	public ResponseEntity<String> saveEmpFallback(Exception e) {
		logger.info("--RESPONSE FROM FALLBACK METHOD---");
		
		return new ResponseEntity<String>("SERVER IS DOWN, PLEASE TRT AFTER SOME TIME", null);
	}
	
	@PutMapping("/updateemployeeaddress")
	//@TimeLimiter(name = "updateEmpTimeLimitter", fallbackMethod = "updateEmpFallback")
	public ResponseEntity<AddressRecord>  updateEmployeeAddress(@RequestBody AddressRecord employee) {	
		logger.info("EmployeeFeignController - updateEmployee-id:"+employee.addressID());		
		empService.updateEmployeeAddress(employee);
		return new ResponseEntity<>(employee, HttpStatus.ACCEPTED);
	}
	
	/*
	 * public ResponseEntity<String> updateEmpFallback(Exception e) {
	 * logger.info("--RESPONSE FROM FALLBACK METHOD---");
	 * 
	 * return new
	 * ResponseEntity<String>("SERVER IS DOWN, PLEASE TRT AFTER SOME TIME", null); }
	 */
	@DeleteMapping("/deleteaddress/{id}")
	public ResponseEntity<HttpStatus> deleteEmployeeAddress(@PathVariable("id") Integer id) {
		logger.info("EmployeeFeignController - deleteEmployeeAddress-id:"+id);
		empService.deleteEmployeeAddress(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

	
}