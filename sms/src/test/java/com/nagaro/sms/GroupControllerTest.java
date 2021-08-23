package com.nagaro.sms;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nagaro.sms.dto.GroupDto;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.service.GroupService;
import com.nagaro.sms.util.Constants;

/**
 * Test class for GroupController
 * 
 * @author komaljain01
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "spring")
@TestInstance(Lifecycle.PER_CLASS)
class GroupControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	GroupService groupService;
	
	@Autowired
	GroupRepository groupRepository;

	
	@BeforeEach
    public void setup() {
		GroupDto groupDto = new GroupDto();
		groupDto.setName("Medical");
		GroupDto response = groupService.addGroup(groupDto);
		Assertions.assertEquals("Medical", response.getName());
		
		GroupDto groupDto2 = new GroupDto();
		groupDto2.setName("NonMedical");
		GroupDto response2 = groupService.addGroup(groupDto2);
		Assertions.assertEquals("NonMedical", response2.getName());
    }
	
	@AfterEach
	void cleanup() {
		groupRepository.deleteAll();
	}
	
	@Test
	void testV1GroupPost() throws JsonProcessingException {

		GroupDto anObject = new GroupDto();
		anObject.setName("MyGroup");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(anObject);
		System.out.println(requestJson);

		try {
			mockMvc.perform(post("/v1/groups").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.name").value("MyGroup"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1GroupGet_NotFound() throws JsonProcessingException {
		try {
			mockMvc.perform(get("/v1/groups/67887898")).andExpect(status().isNotFound())
					.andExpect(jsonPath("$.errorMessage").value(Constants.groupNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1GroupGet_Found() throws JsonProcessingException {
		
		List<GroupDto> groupList = groupService.getAllGroups();

		try {
			mockMvc.perform(get("/v1/groups/" + groupList.get(0).getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value(groupList.get(0).getName())).andExpect(jsonPath("$.id").value(groupList.get(0).getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1GroupGetAll_Found() throws JsonProcessingException {
		
		List<GroupDto> groupList = groupService.getAllGroups();
		try {
			mockMvc.perform(get("/v1/groups/list")).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].name", is(groupList.get(0).getName())))
					.andExpect(jsonPath("$[0].id", is(groupList.get(0).getId())))
					.andExpect(jsonPath("$[1].name", is(groupList.get(1).getName())))
					.andExpect(jsonPath("$[1].id", is(groupList.get(1).getId())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1GroupPut_Success() throws JsonProcessingException {
		GroupDto groupDto = new GroupDto();
		groupDto.setName("GroupPutTest");
		GroupDto response = groupService.addGroup(groupDto);
		Assertions.assertEquals("GroupPutTest", response.getName());

		GroupDto anObject = new GroupDto();
		anObject.setName("ChangedGroupName");
		anObject.setId(response.getId());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(anObject);

		try {
			mockMvc.perform(put("/v1/groups").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1GroupDelete_Success() throws JsonProcessingException {
		GroupDto groupDto = new GroupDto();
		groupDto.setName("GroupDeleteTest");
		GroupDto response = groupService.addGroup(groupDto);
		Assertions.assertEquals("GroupDeleteTest", response.getName());

		try {
			mockMvc.perform(delete("/v1/groups/" + response.getId())).andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
