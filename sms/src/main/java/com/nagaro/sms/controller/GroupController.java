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
import com.nagaro.sms.dto.GroupDto;
import com.nagaro.sms.service.GroupService;
import com.nagaro.sms.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * GroupController handles requests for Group data
 * 
 * @author komaljain01
 *
 */
@RestController
@Api(tags = { "Group APIs" })
@GlobalApiResponses
@RequestMapping("v1/groups")
public class GroupController {
	@Autowired
	GroupService groupService;

	/**
	 * This API registers a new group in system
	 * 
	 * @param group
	 * @return ResponseEntity<Object> of GroupDto and HTTPStatus code
	 */
	@ApiOperation(value = "Add a Group", notes = "This API registers a new group in system", response = GroupDto.class)
	@PostMapping
	public ResponseEntity<Object> registerGroup(@RequestBody GroupDto group) {
		return new ResponseEntity<>(groupService.addGroup(group), HttpStatus.CREATED);

	}

	/**
	 * This API updates an existing group in system
	 * 
	 * @param group
	 * @return ResponseEntity<Object> of GroupDto and HTTPStatus code
	 */
	@ApiOperation(value = "Update a Group", notes = "This API updates an existing group in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.groupNotFound)})
	@PutMapping
	public ResponseEntity<Object> updateGroup(@RequestBody GroupDto group) {
		groupService.updateGroup(group);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * This API deletes an existing group in system
	 * 
	 * @param groupId
	 * @return
	 */
	@ApiOperation(value = "Delete a Group", notes = "This API deletes an existing group in system")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.groupNotFound)})
	@DeleteMapping("/{groupId}")
	public ResponseEntity<Object> deleteGroup(
			@ApiParam(value = "id of Group to be deleted", required = true) @PathVariable Integer groupId) {
		groupService.deleteGroup(groupId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This API fetches group for a given groupId
	 * 
	 * @param groupId
	 * @return
	 */
	@ApiOperation(value = "View a Group", notes = "This API fetches group for a given groupId", response = GroupDto.class)
	@ApiResponses(value = {@ApiResponse(code = 404, message = Constants.groupNotFound)})
	@GetMapping("/{groupId}")
	public ResponseEntity<Object> getGroupById(@ApiParam(value = "id of Group to be retrieved", required = true)@PathVariable Integer groupId) {
		return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
	}

	/**
	 * This API fetches all groups
	 * 
	 * @return ResponseEntity<Object> of List<GroupDto> and HTTPStatus code
	 */
	@ApiOperation(value = "View all groups", notes = "This API fetches all groups", response = GroupDto.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.noDataFound)})
	@GetMapping("/list")
	public ResponseEntity<Object> getAllGroups() {
		return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
	}

}
