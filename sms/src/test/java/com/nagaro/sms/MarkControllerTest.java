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

import java.util.Date;
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
import com.nagaro.sms.dto.MarkDto;
import com.nagaro.sms.dto.StudentDto;
import com.nagaro.sms.dto.SubjectDto;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.repository.MarkRepository;
import com.nagaro.sms.repository.StudentRepository;
import com.nagaro.sms.repository.SubjectRepository;
import com.nagaro.sms.service.GroupService;
import com.nagaro.sms.service.MarkService;
import com.nagaro.sms.service.StudentService;
import com.nagaro.sms.service.SubjectService;
import com.nagaro.sms.util.Constants;

/**
 * Test class for MarkController
 * 
 * @author komaljain01
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "spring")
@TestInstance(Lifecycle.PER_CLASS)
class MarkControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected MarkService markService;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected GroupService groupService;
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	MarkRepository markRepository;
	
	@BeforeEach
	void setup() {
	
		GroupDto groupDto = new GroupDto();
		groupDto.setName("Medical");
		GroupDto response = groupService.addGroup(groupDto);
		Assertions.assertEquals("Medical", response.getName());
		
		StudentDto studentDto =new StudentDto();
		studentDto.setLname("Jain");
		studentDto.setFname("Komal");
		studentDto.setGroupId(response.getId());
		StudentDto studentResponse = studentService.addStudent(studentDto);
		Assertions.assertEquals("Komal", studentResponse.getFname());
		
		SubjectDto subjectDto =new SubjectDto();
		subjectDto.setTitle("Science");
		SubjectDto subjectResponse = subjectService.addSubject(subjectDto);
		Assertions.assertEquals("Science", subjectResponse.getTitle());
		
	}
	
	@AfterEach
	void cleanup() {
		groupRepository.deleteAll();
		subjectRepository.deleteAll();
		studentRepository.deleteAll();
		markRepository.deleteAll();
	}
	
	
	@Test
	void testV1MarkPost() throws JsonProcessingException {
	
		List<StudentDto> studentResponse = studentService.getAllStudents();
		Assertions.assertEquals("Komal", studentResponse.get(0).getFname());

		List<SubjectDto> subjectResponse = subjectService.getAllSubjects();
		Assertions.assertEquals("Science", subjectResponse.get(0).getTitle());

		MarkDto markDto = new MarkDto();
		markDto.setStudentId(studentResponse.get(0).getId());
		markDto.setSubjectId(subjectResponse.get(0).getId());
		markDto.setMark(75);
		markDto.setDate(new Date());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(markDto);
		System.out.println(requestJson);

		try {
			mockMvc.perform(post("/v1/marks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.mark").value("75"))
					.andExpect(jsonPath("$.subjectId").value(markDto.getSubjectId()))
					.andExpect(jsonPath("$.studentId").value(markDto.getStudentId()))
					.andExpect(jsonPath("$.id").isNotEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1MarkPost_failure_subjectId_notfound() throws JsonProcessingException {
	
		List<StudentDto> studentResponse = studentService.getAllStudents();
		Assertions.assertEquals("Komal", studentResponse.get(0).getFname());

		MarkDto markDto = new MarkDto();
		markDto.setStudentId(studentResponse.get(0).getId());
		markDto.setSubjectId(6767676);
		markDto.setMark(75);
		markDto.setDate(new Date());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(markDto);
		System.out.println(requestJson);

		try {
			mockMvc.perform(post("/v1/marks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.errorMessage").value(Constants.subjectNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1MarkPost_failure_studentid_notfound() throws JsonProcessingException {
	
		List<SubjectDto> subjectResponse = subjectService.getAllSubjects();
		Assertions.assertEquals("Science", subjectResponse.get(0).getTitle());

		MarkDto markDto = new MarkDto();
		markDto.setStudentId(46566);
		markDto.setSubjectId(subjectResponse.get(0).getId());
		markDto.setMark(75);
		markDto.setDate(new Date());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(markDto);
		System.out.println(requestJson);

		try {
			mockMvc.perform(post("/v1/marks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.errorMessage").value(Constants.studentNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1MarksGet_NotFound() throws JsonProcessingException {
		
		try {
			mockMvc.perform(get("/v1/marks/67887898")).andExpect(status().isNotFound())
					.andExpect(jsonPath("$.errorMessage").value(Constants.markNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1MarksGet_Found() throws JsonProcessingException {
		// insert mark
		testV1MarkPost();
		List<MarkDto> marksList = markService.getAllMarks();

		try {
			mockMvc.perform(get("/v1/marks/" + marksList.get(0).getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$.mark").value(marksList.get(0).getMark()))
					.andExpect(jsonPath("$.id").isNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1MarksGetAllMarks_Found() throws JsonProcessingException {
		// insert mark
		testV1MarkPost();
		List<MarkDto> marksList = markService.getAllMarks();
		System.out.println(marksList.size());

		try {
			mockMvc.perform(get("/v1/marks/list")).andExpect(status().isOk())
					.andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].mark", is(marksList.get(0).getMark())))
					.andExpect(jsonPath("$[0].id").isNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1MarkDelete_Success() throws JsonProcessingException {
		
		// insert mark
		testV1MarkPost();
		
		List<MarkDto> marksList = markService.getAllMarks();
		

		try {
			mockMvc.perform(delete("/v1/marks/" + marksList.get(0).getId())).andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1MarksPut_Success() throws JsonProcessingException {
		// insert mark
		testV1MarkPost();
		List<MarkDto> marksList = markService.getAllMarks();
		
		MarkDto markDto = new MarkDto();
		markDto.setId(marksList.get(0).getId());
		markDto.setStudentId(marksList.get(0).getStudentId());
		markDto.setSubjectId(marksList.get(0).getSubjectId());
		markDto.setMark(80);
		markDto.setDate(marksList.get(0).getDate());
		ObjectMapper mapper2 = new ObjectMapper();
		mapper2.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow2 = mapper2.writer().withDefaultPrettyPrinter();
		String requestJson = ow2.writeValueAsString(markDto);

		try {
			mockMvc.perform(put("/v1/marks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1MarksPut_failure_mark_not_found() throws JsonProcessingException {
		// insert mark
		testV1MarkPost();
		List<MarkDto> marksList = markService.getAllMarks();
		
		MarkDto markDto = new MarkDto();
		markDto.setId(5678);
		markDto.setStudentId(marksList.get(0).getStudentId());
		markDto.setSubjectId(marksList.get(0).getSubjectId());
		markDto.setMark(80);
		markDto.setDate(marksList.get(0).getDate());
		ObjectMapper mapper2 = new ObjectMapper();
		mapper2.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow2 = mapper2.writer().withDefaultPrettyPrinter();
		String requestJson = ow2.writeValueAsString(markDto);

		try {
			mockMvc.perform(put("/v1/marks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Content-Type", "application/json"))
			.andExpect(jsonPath("$.errorMessage").value(Constants.markNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1MarksPut_failure_mandatory_field_missing() throws JsonProcessingException {
		// insert mark
		testV1MarkPost();
		List<MarkDto> marksList = markService.getAllMarks();
		
		MarkDto markDto = new MarkDto();
		markDto.setId(null);
		markDto.setStudentId(marksList.get(0).getStudentId());
		markDto.setSubjectId(marksList.get(0).getSubjectId());
		markDto.setMark(80);
		markDto.setDate(marksList.get(0).getDate());
		ObjectMapper mapper2 = new ObjectMapper();
		mapper2.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow2 = mapper2.writer().withDefaultPrettyPrinter();
		String requestJson = ow2.writeValueAsString(markDto);

		try {
			mockMvc.perform(put("/v1/marks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Content-Type", "application/json"))
			.andExpect(jsonPath("$.errorMessage").value(Constants.genericRequiredFieldsMissingMsg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectMarksListGet_Success() throws JsonProcessingException {
		testV1MarkPost();
		
		try {
			mockMvc.perform(get("/v1/marks/subjectMarkList/studentId/"+studentService.getAllStudents().get(0).getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].mark", is(75)))
					.andExpect(jsonPath("$[0].id").isNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectMarksListGet_failure() throws JsonProcessingException {
		try {
			mockMvc.perform(get("/v1/marks/subjectMarkList/studentId/565577")).andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errorMessage").value(Constants.noDataFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1TotalMarksGet_Success() throws JsonProcessingException {
		testV1MarkPost();
		
		try {
			mockMvc.perform(get("/v1/marks/total/studentId/"+studentService.getAllStudents().get(0).getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$").isNumber())
					.andExpect(jsonPath("$", is(75)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
