package com.girish.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.girish.exception.EmployeeNotFoundException;
import com.girish.model.Employee;
import com.girish.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private EmployeeService empService;
	
	private Employee employee;
	
	
	@BeforeEach
    public void setup(){        
        employee = Employee.builder()  
        		.employeeID(30)
                .firstName("rajendra")
                .lastName("prasad")
                .sex("male")
                .employeeType("permanent")
                .build();
    }    
	
	@Test
	public void addEmployeeTest() throws Exception {
		 
		Mockito.when(empService.addEmployee(employee)).thenReturn(employee);
		String requestBody = objectMapper.writeValueAsString(employee);
		mockMvc.perform(post("/insertemployee")
				.contentType("application/json")
				.content(requestBody))
		        .andExpect(status().isCreated());
		        
	   }
	@Test
	public void getEmployeeByIdNotFoundTest() throws Exception {
		Mockito.when(empService.getByEmployeeId(29)).thenThrow(EmployeeNotFoundException.class);
		mockMvc.perform(get("/employeebyid/29").contentType("application/json"))
		       .andExpect(status().isNotFound())
		       .andDo(print());
	}
	@Test
	public void getEmployeeByIdTest() throws Exception {		
		Employee employee = Employee.builder()  
        		.employeeID(3)
                .firstName("raju")
                .lastName("prasad")
                .sex("male")
                .employeeType("permanent")
                .build();
		Mockito.when(empService.getByEmployeeId(3)).thenReturn(Optional.of(employee));
		String requestBody = objectMapper.writeValueAsString(employee);
		mockMvc.perform(get("/employeebyid/3").contentType("application/json")
				.content(requestBody))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.firstName",is("raju")))
		       .andDo(print());
	}
	
	@Test
	public void getAllEmployeesTest() throws Exception {		
		Mockito.when(empService.getAllEmployees()).thenReturn(List.of(employee));
		String requestBody = objectMapper.writeValueAsString(employee);
		mockMvc.perform(get("/getallemployees")
				.contentType("application/json")
				.content(requestBody))			
		       .andExpect(status().isOk())		      
		       .andDo(print());
	   
	}
	@Disabled
	@Test
	public void updateEmployeeTest() throws Exception{
		
		given(empService.updateEmployee(employee)).willReturn(employee);
		String jsonString = "{\n" + "\"employeeID\":2,\n" + "\"firstName\":\"mang123\",\n"
				+ "\"lastName\":\"puram123\",\n" + "\"sex\":\"37\"\n" + "}";
		//String requestBody = objectMapper.writeValueAsString(jsonString);
		mockMvc.perform(put("/updateemployee")
				.contentType("application/json")
				.content(jsonString))			
		       .andExpect(status().isOk())	
		       .andExpect(jsonPath("$.firstName",is("mang123")))
		       .andDo(print());
		
	}
	@Disabled
	@Test
	public void deletePersnoneTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/delete/{id}", 2)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = (MvcResult) mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String responseData = result.getResponse().getContentAsString();
		System.out.println("delete>>responseData>>" + responseData);
		// Assert.assertEquals("NO_CONTENT", responseData);

	}
	
	
	
	
	
	
	
	}
	
	
    

