package com.girish.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.girish.model.Employee;
import com.girish.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceImplTest {
	
	@Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;
    
    private Employee employee;
    
    @BeforeEach
    public void setup(){        
        employee = Employee.builder()               
                .firstName("bushuu")
                .lastName("jenny")
                .sex("Female")
                .employeeType("Contract")
                .build();
    }    
    
    @Order(5)
    @Test
    public void deleteEmployeeTest(){        
        int employeeId = 21;
        willDoNothing().given(employeeRepository).deleteById(employeeId);        
        employeeService.deleteEmployee(employeeId);        
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
    @Order(4)
    @Test
    public void updateEmployeeTest(){        
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setLastName("bunnu");
        employee.setFirstName("Ram");       
        Employee updatedEmployee = employeeService.updateEmployee(employee);       
        assertThat(updatedEmployee.getLastName()).isEqualTo("bunnu");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }
    
    
    @Order(1)	
    @Test
    public void addEmployeeTest(){     	
    	given(employeeRepository.save(employee)).willReturn(employee);  
        Employee savedEmployee = employeeService.addEmployee(employee);
        System.out.println(savedEmployee);        
        assertThat(savedEmployee).isNotNull();  	
    }
    
    @Order(2)	
    @Test
    public void findAllEmployeesTest(){ 
    	given(employeeRepository.findAll()).willReturn(List.of(employee));
    	List<Employee> employeeList = employeeService.getAllEmployees();  
    	System.out.println("employeeList--"+employeeList.size());
        assertThat(employeeList.size()).isGreaterThan(0);	
    }
    
    @Order(3)	
    @Test
    public void findByEmployeeIdTest() {
    	
    	// Given
        Employee employee = this.buildTestingEmployee();
        // When
        when(employeeRepository.findById(21)).thenReturn(Optional.of(employee));
        Optional<Employee> returnedEmployee = this.employeeService.getByEmployeeId(21);
        // Then
        assertEquals(employee.getEmployeeID(), returnedEmployee.get().getEmployeeID());
        verify(this.employeeRepository).findById(21);    	
    }
    
    public Employee buildTestingEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeID(21);
        employee.setFirstName("mang123");
        employee.setLastName("puram123");
        employee.setSex("Male");
        employee.setEmployeeType("Permanent");
        return employee;
    }

}
