package com.girish.dto;

import com.girish.exception.EmployeeNotFoundException;

public record AddressRecord(int addressID,String doorNo,String street,String city,String employeeId) {
	/*public  AddressRecord(int addressID,String doorNo,String street,String city,String employeeId) {	
	this.addressID = 0;
	this.city = "";
	this.doorNo = "";
	this.employeeId = "";
	this.street = "";
	if (addressID <= 0) {
	      new EmployeeNotFoundException("Employee-"+employeeId+" not found with the given ID.");
	  }
	}*/
}
