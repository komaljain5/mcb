package com.nagaro.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagaro.sms.annotation.GlobalApiResponses;
import com.nagaro.sms.dto.StudentDto;
import com.nagaro.sms.service.StudentService;
import com.nagaro.sms.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * StudentController handles requests for Student data
 * 
 * @author komaljain01
 *
 */
@RestController
@Api(tags = {"Student APIs"})
@GlobalApiResponses
@RequestMapping("v1/students")
public class StudentController {

	@Autowired
	StudentService studentService;

	/**
	 * This API registers a new student in system
	 * 
	 * @param student
	 * @return ResponseEntity<Object> of StudentDto and HTTPStatus code
	 */
	@ApiOperation(value = "Add a Student", notes = "This API registers a new student in system", response = StudentDto.class)
	@PostMapping
	public ResponseEntity<Object> registerStudent(@RequestBody StudentDto student) {
		return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.CREATED);

	}

	/**
	 * This API updates an existing student in system
	 * 
	 * @param student
	 * @return ResponseEntity<Object> of StudentDto and HTTPStatus code
	 */
	@ApiOperation(value = "Update a Student", notes = "This API updates an existing student in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.studentNotFound) })
	@PutMapping
	public ResponseEntity<Object> updateStudent(@RequestBody StudentDto student) {
		studentService.updateStudent(student);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * This API deletes an existing student in system
	 * 
	 * @param studentId
	 * @return
	 */
	@ApiOperation(value = "Delete a Student", notes = "This API deletes an existing student in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.studentNotFound) })
	@DeleteMapping("/{studentId}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Integer studentId) {
		studentService.deleteStudent(studentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This API fetches student for a given studentId
	 * 
	 * @param studentId
	 * @return
	 */
	@ApiOperation(value = "View a Student", notes = "This API fetches student for a given studentId", response = StudentDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.studentNotFound) })
	@GetMapping("/{studentId}")
	public ResponseEntity<Object> getStudentById(@PathVariable Integer studentId) {
		return new ResponseEntity<>(studentService.getStudentById(studentId), HttpStatus.OK);
	}

	/**
	 * This API fetches all students
	 * 
	 * @return ResponseEntity<Object> of List<StudentDto> and HTTPStatus code
	 */
	@ApiOperation(value = "View all students", notes = "This API fetches all students", response = StudentDto.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound) })
	@GetMapping("/list")
	public ResponseEntity<Object> getAllStudents() {
		return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
	}


}
