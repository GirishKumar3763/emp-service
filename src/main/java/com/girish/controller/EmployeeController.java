package com.girish.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.girish.model.Employee;
import com.girish.repository.EmployeeRepository;

@CrossOrigin
@RestController
public class EmployeeController {	
	
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping("/employeebyid/{id}")
	public Employee getByEmployeeId(@PathVariable("id") int id) {
		Employee emp = empRepo.findById(id).get();
		System.out.println(emp.getFirstName());
		return emp;
	 
	}
	
	@GetMapping("/getallemployees")
	public List<Employee> getAllEmployees() {
		List<Employee> emps = empRepo.findAll();
		return emps;		
		
	}

	@DeleteMapping("/delete/{id}")
	public void deleteEmployee(@PathVariable("id") Integer id) {
		Employee emp = empRepo.findById(id).get();
		empRepo.delete(emp);
		
	}

	@PostMapping("/insertemployee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void insertEmployee(@RequestBody Employee employee) {
		empRepo.save(employee);
		
	}

	@PutMapping("/updateemployee/{id}")
	public Employee  updateEmployee(@PathVariable("id") int id) {		
		Employee  emp = empRepo.findById(id).get();
		emp.setFirstName("ganesh");
		emp.setLastName("kanna");
		emp.setSex("Female");
		emp.setEmployeeType("Contract");
		empRepo.save(emp);		
		return emp;	
	}
	
	
}