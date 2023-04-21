package com.neel.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neel.entity.Employee;
import com.neel.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
//	private EmployeeDAO employeeDAO;
//	
//	//quick and dirty: inject employee dao (use constructor injection)
//	
//	@Autowired
//	public EmployeeRestController(EmployeeDAO theEmployeeDAO) {
//		employeeDAO = theEmployeeDAO;
//	}          //after implementing service layer this code is commented
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRestController (EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	//expose "/employees" and return list of employee
	@GetMapping("/employees")
	public List<Employee> findAll(){
		//return employeeDAO.findAll();
		return employeeService.findAll();
	}
	
	//add mapping for GET / employees/ {employeeId}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee theEmployee = employeeService.findById(employeeId);
		
		if (theEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		return theEmployee;
	}
	
	//add mapping for post / employees - add new employee
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		//also just in case they pass an id in JSON.. set id to 0
		//this is to force a save of new item.. instead of update
		
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	// add mapping to PUT / employee - update existing employee
	
	@PutMapping("/employees")
	public  Employee updateEmployee(@RequestBody Employee theEmployee) {
		
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	// delete mapping DELETE / EMPLOYEES/ {employeeId}
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		//throw exception if null
		if(tempEmployee == null) {
			throw new RuntimeException("Employee id not found - "+ employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Delete employee id - " + employeeId;
	}
	
	@RequestMapping("/test")
	public String tester() {
		return "just for testing";
	}

}
