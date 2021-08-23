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
import com.nagaro.sms.service.MarkService;
import com.nagaro.sms.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * MarkController handles requests for Mark data
 * 
 * @author komaljain01
 *
 */
@RestController
@Api(tags = {"Mark APIs"})
@GlobalApiResponses
@RequestMapping("v1/marks")
public class MarkController {

	@Autowired
	MarkService markService;

	/**
	 * This API registers a new mark in system
	 * 
	 * @param mark
	 * @return ResponseEntity<Object> of MarkDto and HTTPStatus code
	 */
	@ApiOperation(value = "Add a Mark", notes = "This API registers a new mark in system", response = MarkDto.class)
	@PostMapping
	public ResponseEntity<Object> registerMark(@RequestBody MarkDto mark) {
		return new ResponseEntity<>(markService.addMark(mark), HttpStatus.CREATED);

	}

	/**
	 * This API updates an existing mark in system
	 * 
	 * @param mark
	 * @return ResponseEntity<Object> of MarkDto and HTTPStatus code
	 */
	@ApiOperation(value = "Update a Mark", notes = "This API updates an existing mark in system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.updated),
			@ApiResponse(code = 404, message = Constants.markNotFound)})
	@PutMapping
	public ResponseEntity<Object> updateMark(@RequestBody MarkDto mark) {
		markService.updateMark(mark);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * This API deletes an existing mark in system
	 * 
	 * @param markId
	 * @return
	 */
	@ApiOperation(value = "Delete a Mark", notes = "This API deletes an existing mark in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.markNotFound)})
	@DeleteMapping("/{markId}")
	public ResponseEntity<Object> deleteMark(@ApiParam(value = "Id of Mark to be deleted", required = true)@PathVariable Integer markId) {
		markService.deleteMark(markId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This API fetches mark for a given markId
	 * 
	 * @param markId
	 * @return
	 */
	@ApiOperation(value = "View a Mark", notes = "This API fetches mark for a given markId", response = MarkDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.markNotFound)})
	@GetMapping("/{markId}")
	public ResponseEntity<Object> getMarkById(@ApiParam(value = "Id of Mark to be retrieved", required = true) @PathVariable Integer markId) {
		return new ResponseEntity<>(markService.getMarkById(markId), HttpStatus.OK);
	}

	/**
	 * This API fetches all marks
	 * 
	 * @return ResponseEntity<Object> of List<MarkDto> and HTTPStatus code
	 */
	@ApiOperation(value = "View all marks", notes = "This API fetches all marks", response = MarkDto.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound)})
	@GetMapping("/list")
	public ResponseEntity<Object> getAllMarks() {
		return new ResponseEntity<>(markService.getAllMarks(), HttpStatus.OK);
	}
	
	/**
	 * This API fetches totalMarks for a student id
	 * 
	 * @param studentId
	 * @return
	 */
	@ApiOperation(value = "View total Marks for a student", notes = "This API fetches total marks of a student", response = MarkDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound)})
	@GetMapping("/total/studentId/{studentId}")
	public ResponseEntity<Object> getMarkByStudentId(@ApiParam(value = "id of Student whose total marks shall be retrieved", required = true)@PathVariable Integer studentId) {
		return new ResponseEntity<>(markService.getMarkForStudentId(studentId), HttpStatus.OK);
	}
	
	
	/**
	 * This API fetches returns list of marks for each subject for given student id
	 * 
	 * @param studentId
	 * @return
	 */
	@ApiOperation(value = "View list of marks for each subject for a student", notes = "This API returns list of marks for each subject for a particular StudentId", response = MarkDto.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound)})
	@GetMapping("/subjectMarkList/studentId/{studentId}")
	public ResponseEntity<Object> getSubjectMarkListByStudentId(@ApiParam(value = "id of Student whose subject marks list shall be retrieved", required = true)@PathVariable Integer studentId) {
		return new ResponseEntity<>(markService.getSubjectMarkListByStudentId(studentId), HttpStatus.OK);
	}


}
