package com.girish.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="employee")
public class Employee {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
    private int employeeID;
	
	@Column(name = "first_name")
    private String firstName;
	
	@Column(name = "last_name")
    private String lastName;
	
	@Column(name = "sex")
    private String sex;
	
	@Column(name = "employee_type")
    private String employeeType;		
	

}