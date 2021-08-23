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
import com.nagaro.sms.dto.SubjectDto;
import com.nagaro.sms.service.SubjectService;
import com.nagaro.sms.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * SubjectController handles requests for Subject data
 * 
 * @author komaljain01
 *
 */
@RestController
@Api(tags = {"Subject APIs"})
@GlobalApiResponses
@RequestMapping("v1/subjects")
public class SubjectController {
	
	@Autowired
	SubjectService subjectService;

	@ApiOperation(value = "Add a Subject", notes = "This API registers a new subject in system", response = SubjectDto.class)
	@PostMapping
	public ResponseEntity<Object> registerSubject(@RequestBody SubjectDto subject) {
		return new ResponseEntity<>(subjectService.addSubject(subject), HttpStatus.CREATED);

	}

	@ApiOperation(value = "Update a Subject", notes = "This API updates an existing subject in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.subjectNotFound) })
	@PutMapping
	public ResponseEntity<Object> updateSubject(@RequestBody SubjectDto subject) {
		subjectService.updateSubject(subject);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@ApiOperation(value = "Delete a Subject", notes = "This API deletes an existing subject in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.subjectNotFound) })
	@DeleteMapping("/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer subjectId) {
		subjectService.deleteSubject(subjectId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "View a Subject", notes = "This API fetches subject for a given subjectId", response = SubjectDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.subjectNotFound) })
	@GetMapping("/{subjectId}")
	public ResponseEntity<Object> getSubjectById(@PathVariable Integer subjectId) {
		return new ResponseEntity<>(subjectService.getSubjectById(subjectId), HttpStatus.OK);
	}

	@ApiOperation(value = "View all subjects", notes = "This API fetches all subjects", response = SubjectDto.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound) })
	@GetMapping("/list")
	public ResponseEntity<Object> getAllSubjects() {
		return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
	}

}
