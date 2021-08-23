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

import java.util.List;

import org.junit.jupiter.api.AfterEach;
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
import com.nagaro.sms.dto.SubjectDto;
import com.nagaro.sms.repository.SubjectRepository;
import com.nagaro.sms.service.SubjectService;
import com.nagaro.sms.util.Constants;

/**
 * Test class for SubjectController
 * 
 * @author komaljain01
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "spring")
@TestInstance(Lifecycle.PER_CLASS)
class SubjectControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectService subjectService;

	@AfterEach
	void cleanup() {
		subjectRepository.deleteAll();
	}

	@Test
	void testV1SubjectPost() throws JsonProcessingException {

		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setTitle("English Communication");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(subjectDto);

		try {
			mockMvc.perform(post("/v1/subjects").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.title").value("English Communication"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1SubjectGet_NotFound() throws JsonProcessingException {

		try {
			mockMvc.perform(get("/v1/subjects/435435")).andExpect(status().isNotFound())
					.andExpect(jsonPath("$.errorMessage").value(Constants.subjectNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1SubjectGet_Found() throws JsonProcessingException {
		testV1SubjectPost();

		List<SubjectDto> subjectsList = subjectService.getAllSubjects();

		try {
			mockMvc.perform(get("/v1/subjects/" + subjectsList.get(0).getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$.title").value(subjectsList.get(0).getTitle()))
					.andExpect(jsonPath("$.id").value(subjectsList.get(0).getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1SubjectGetAll_Found() throws JsonProcessingException {
		testV1SubjectPost();

		List<SubjectDto> subjectsList = subjectService.getAllSubjects();
		
		try {
			mockMvc.perform(get("/v1/subjects/list")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].title", is(subjectsList.get(0).getTitle())))
					.andExpect(jsonPath("$[0].id").isNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1SubjectPut_Success() throws JsonProcessingException {
		testV1SubjectPost();

		List<SubjectDto> subjectsList = subjectService.getAllSubjects();

		SubjectDto anObject = new SubjectDto();
		anObject.setTitle("ChangedSubjectName");
		anObject.setId(subjectsList.get(0).getId());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(anObject);

		try {
			mockMvc.perform(put("/v1/subjects").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void testV1SubjectDelete_Success() throws JsonProcessingException {
		testV1SubjectPost();

		List<SubjectDto> subjectsList = subjectService.getAllSubjects();

		try {
			mockMvc.perform(delete("/v1/subjects/" + subjectsList.get(0).getId())).andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
