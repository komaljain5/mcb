package com.nagaro.sms;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import com.nagaro.sms.dto.StudentDto;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.repository.StudentRepository;
import com.nagaro.sms.service.GroupService;
import com.nagaro.sms.service.StudentService;
import com.nagaro.sms.util.Constants;

/**
 * Test class for StudentController
 * 
 * @author komaljain01
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "spring")
@TestInstance(Lifecycle.PER_CLASS)
class StudentControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected GroupService groupService;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@AfterEach
	void cleanup() {
		groupRepository.deleteAll();
		studentRepository.deleteAll();
	}
	
	@Test
	void testV1StudentPost() throws JsonProcessingException {
		
		GroupDto groupDto = new GroupDto();
		groupDto.setName("Medical");
		GroupDto response = groupService.addGroup(groupDto);
		Assertions.assertEquals("Medical", response.getName());
		
		StudentDto studentDto =new StudentDto();
		studentDto.setLname("Jain");
		studentDto.setFname("Komal");
		studentDto.setGroupId(response.getId());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(studentDto);
		System.out.println(requestJson);

		try {
			mockMvc.perform(post("/v1/students").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.fname").value("Komal"))
					.andExpect(jsonPath("$.groupId").value(studentDto.getGroupId()))
					.andExpect(jsonPath("$.lname").value("Jain"))
					.andExpect(jsonPath("$.id").isNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1StudentGet_NotFound() throws JsonProcessingException {
		
		try {
			mockMvc.perform(get("/v1/students/67887898")).andExpect(status().isNotFound())
					.andExpect(jsonPath("$.errorMessage").value(Constants.studentNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1StudentGet_Found() throws JsonProcessingException {
		testV1StudentPost();

		try {
			mockMvc.perform(get("/v1/students/" + studentService.getAllStudents().get(0).getId()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.fname").value(studentService.getAllStudents().get(0).getFname()))
					.andExpect(jsonPath("$.id").value(studentService.getAllStudents().get(0).getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1StudentDelete_Success() throws JsonProcessingException {
		testV1StudentPost();
		StudentDto response = studentService.getAllStudents().get(0);

		try {
			mockMvc.perform(delete("/v1/students/" + response.getId())).andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1StudentPut_Success() throws JsonProcessingException {
		testV1StudentPost();
		StudentDto response = studentService.getAllStudents().get(0);
		
		StudentDto request = new StudentDto();
		request.setGroupId(response.getGroupId());
		request.setId(response.getId());
		request.setFname("Charu");
		request.setLname("Jain");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(request);

		try {
			mockMvc.perform(put("/v1/students").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1StudentsGetAll() throws JsonProcessingException {

		testV1StudentPost();
		StudentDto response = studentService.getAllStudents().get(0);

		try {
			mockMvc.perform(get("/v1/students/list")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].groupId", is(response.getGroupId())))
					.andExpect(jsonPath("$[0].fname", is(response.getFname())))
					.andExpect(jsonPath("$[0].lname", is(response.getLname())))
					.andExpect(jsonPath("$[0].id", is(response.getId())))
					.andExpect(jsonPath("$[0].id").isNotEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
