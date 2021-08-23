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
import com.nagaro.sms.dto.MarkDto;
import com.nagaro.sms.dto.SubjectTeacherDto;
import com.nagaro.sms.service.SubjectTeacherService;
import com.nagaro.sms.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * SubjectTeacherController handles requests for SubjectTeacher data
 * 
 * @author komaljain01
 *
 */
@RestController
@Api(tags = {"SubjectTeacher APIs"})
@GlobalApiResponses
@RequestMapping("v1/teachers")
public class SubjectTeacherController {

	@Autowired
	SubjectTeacherService subjectTeacherService;

	/**
	 * This API registers a new subjectTeacher in system
	 * 
	 * @param subjectTeacher
	 * @return ResponseEntity<Object> of SubjectTeacherDto and HTTPStatus code
	 */
	@ApiOperation(value = "Add a SubjectTeacher", notes = "This API registers a new subjectTeacher in system", response = SubjectTeacherDto.class)
	@PostMapping
	public ResponseEntity<Object> registerSubjectTeacher(@RequestBody SubjectTeacherDto subjectTeacher) {
		return new ResponseEntity<>(subjectTeacherService.addSubjectTeacher(subjectTeacher), HttpStatus.CREATED);

	}

	/**
	 * This API updates an existing subjectTeacher in system
	 * 
	 * @param subjectTeacher
	 * @return ResponseEntity<Object> of SubjectTeacherDto and HTTPStatus code
	 */
	@ApiOperation(value = "Update a SubjectTeacher", notes = "This API updates an existing subjectTeacher in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.subjectTeacherNotFound) })
	@PutMapping
	public ResponseEntity<Object> updateSubjectTeacher(@RequestBody SubjectTeacherDto subjectTeacher) {
		subjectTeacherService.updateSubjectTeacher(subjectTeacher);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * This API deletes an existing subjectTeacher in system
	 * 
	 * @param subjectTeacherId
	 * @return
	 */
	@ApiOperation(value = "Delete a SubjectTeacher", notes = "This API deletes an existing subjectTeacher in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.subjectTeacherNotFound) })
	@DeleteMapping("/{subjectTeacherId}")
	public ResponseEntity<Object> deleteSubjectTeacher(@PathVariable Integer subjectTeacherId) {
		subjectTeacherService.deleteSubjectTeacher(subjectTeacherId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This API fetches subjectTeacher for a given subjectTeacherId
	 * 
	 * @param subjectTeacherId
	 * @return
	 */
	@ApiOperation(value = "View a SubjectTeacher", notes = "This API fetches subjectTeacher for a given subjectTeacherId", response = SubjectTeacherDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.subjectTeacherNotFound) })
	@GetMapping("/{subjectTeacherId}")
	public ResponseEntity<Object> getSubjectTeacherById(@PathVariable Integer subjectTeacherId) {
		return new ResponseEntity<>(subjectTeacherService.getSubjectTeacherByTeacherId(subjectTeacherId),
				HttpStatus.OK);
	}

	/**
	 * This API fetches all subjectTeachers
	 * 
	 * @return ResponseEntity<Object> of List<SubjectTeacherDto> and HTTPStatus code
	 */
	@ApiOperation(value = "View all subjectTeachers", notes = "This API fetches all subjectTeachers", response = SubjectTeacherDto.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound) })
	@GetMapping("/list")
	public ResponseEntity<Object> getAllSubjectTeachers() {
		return new ResponseEntity<>(subjectTeacherService.getAllSubjectTeachers(), HttpStatus.OK);
	}
	
	/**
	 * This API fetches returns list of marks for each subject for given student id
	 * 
	 * @param markId
	 * @return
	 */
	@ApiOperation(value = "View student count for a teacher", notes = "This API returns count of students for a particular subjectTeacherId", response = MarkDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound) })
	@GetMapping("/{subjectTeacherId}/students/count")
	public ResponseEntity<Object> getStudentCountForSubjectTeacher(@PathVariable Integer subjectTeacherId) {
		return new ResponseEntity<>(subjectTeacherService.getStudentCountForSubjectTeacher(subjectTeacherId), HttpStatus.OK);
	}

}
