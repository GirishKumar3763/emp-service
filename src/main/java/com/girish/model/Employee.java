package com.girish.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


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
	
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", firstName=" + firstName + ", lastName=" + lastName + ", sex="
				+ sex + ", employeeType=" + employeeType + "]";
	}
	public Employee() {}

	public Employee(int employeeID, String firstName, String lastName, String sex, String employeeType) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.employeeType = employeeType;
	}
	
	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	
	
	
	

}