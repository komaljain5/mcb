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
import com.nagaro.sms.dto.SubjectTeacherDto;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.repository.MarkRepository;
import com.nagaro.sms.repository.StudentRepository;
import com.nagaro.sms.repository.SubjectRepository;
import com.nagaro.sms.repository.SubjectTeacherRepository;
import com.nagaro.sms.service.GroupService;
import com.nagaro.sms.service.MarkService;
import com.nagaro.sms.service.StudentService;
import com.nagaro.sms.service.SubjectService;
import com.nagaro.sms.service.SubjectTeacherService;
import com.nagaro.sms.util.Constants;

/**
 * Test class for SubjectTeacherController
 * 
 * @author komaljain01
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "spring")
@TestInstance(Lifecycle.PER_CLASS)
class SubjectTeacherControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	protected GroupService groupService;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	SubjectTeacherService subjectTeacherService;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected StudentRepository studentRepository;
	
	@Autowired
	protected SubjectTeacherRepository teacherRepository;
	
	@Autowired
	MarkRepository markRepository;
	
	@Autowired
	protected MarkService markService;
	
	
	@AfterEach
	void cleanup() {
		groupRepository.deleteAll();
		subjectRepository.deleteAll();
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
		markRepository.deleteAll();
	}
	
	@Test
	void testV1SubjectTeacherPost_success() throws JsonProcessingException {
		
		GroupDto groupDto = new GroupDto();
		groupDto.setName("Medical");
		GroupDto groupResponse = groupService.addGroup(groupDto);
		Assertions.assertEquals("Medical", groupResponse.getName());
		
		StudentDto studentDto =new StudentDto();
		studentDto.setLname("Jain");
		studentDto.setFname("Komal");
		studentDto.setGroupId(groupResponse.getId());
		StudentDto studentResponse = studentService.addStudent(studentDto);
		Assertions.assertEquals("Komal", studentResponse.getFname());
		
		StudentDto studentDto2 =new StudentDto();
		studentDto2.setLname("Banerjee");
		studentDto2.setFname("Tanmoy");
		studentDto2.setGroupId(groupResponse.getId());
		StudentDto studentResponse2 = studentService.addStudent(studentDto2);
		Assertions.assertEquals("Tanmoy", studentResponse2.getFname());
	
		SubjectDto subjectDto =new SubjectDto();
		subjectDto.setTitle("Science");
		SubjectDto subjectResponse = subjectService.addSubject(subjectDto);
		Assertions.assertEquals("Science", subjectResponse.getTitle());
		
		MarkDto markDto = new MarkDto();
		markDto.setStudentId(studentResponse.getId());
		markDto.setSubjectId(subjectResponse.getId());
		markDto.setMark(97);
		markDto.setDate(new Date());
		MarkDto markResponse = markService.addMark(markDto);
		Assertions.assertEquals(97, markResponse.getMark());
		
		MarkDto markDto2 = new MarkDto();
		markDto2.setStudentId(studentResponse2.getId());
		markDto2.setSubjectId(subjectResponse.getId());
		markDto2.setMark(75);
		markDto2.setDate(new Date());
		MarkDto markResponse2 = markService.addMark(markDto2);
		Assertions.assertEquals(75, markResponse2.getMark());

		SubjectTeacherDto teacherDto = new SubjectTeacherDto();
		teacherDto.setGroupId(groupResponse.getId());
		teacherDto.setSubjectId(subjectResponse.getId());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDto);

		try {
			mockMvc.perform(post("/v1/teachers").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.id").isNumber())
					.andExpect(jsonPath("$.subjectId").value(subjectResponse.getId()))
					.andExpect(jsonPath("$.groupId").value(groupResponse.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectPost_mandatory_field_missing_failure() throws JsonProcessingException {
		
		SubjectTeacherDto teacherDto = new SubjectTeacherDto();
		teacherDto.setGroupId(null);
		teacherDto.setSubjectId(null);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDto);

		try {
			mockMvc.perform(post("/v1/teachers").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.errorMessage").value(Constants.subjectTeacherDtoRequiredFieldsMsg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectPost_groupid_not_found_failure() throws JsonProcessingException {
		
		SubjectTeacherDto teacherDto = new SubjectTeacherDto();
		teacherDto.setGroupId(56789);
		teacherDto.setSubjectId(34567);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDto);

		try {
			mockMvc.perform(post("/v1/teachers").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(header().string("Content-Type", "application/json"))
					.andExpect(jsonPath("$.errorMessage").value(Constants.groupNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectTeacherGet_NotFound() throws JsonProcessingException {

		try {
			mockMvc.perform(get("/v1/teachers/435435")).andExpect(status().isNotFound())
					.andExpect(jsonPath("$.errorMessage").value(Constants.subjectTeacherNotFound));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testV1SubjectTeacherGet_Found() throws JsonProcessingException {
		
		testV1SubjectTeacherPost_success();
		
		List<SubjectTeacherDto> teachersList = subjectTeacherService.getAllSubjectTeachers();

		try {
			mockMvc.perform(get("/v1/teachers/" + teachersList.get(0).getId())).andExpect(status().isOk())
					.andExpect(jsonPath("$.subjectId").value(teachersList.get(0).getSubjectId()))
					.andExpect(jsonPath("$.groupId").value(teachersList.get(0).getGroupId()))
					.andExpect(jsonPath("$.id").value(teachersList.get(0).getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1StudentCountForATeacher_success() throws JsonProcessingException {
		
		testV1SubjectTeacherPost_success();
		
		List<SubjectTeacherDto> teachersList = subjectTeacherService.getAllSubjectTeachers();

		try {
			mockMvc.perform(get("/v1/teachers/"+teachersList.get(0).getId()+"/students/count")).andExpect(status().isOk())
					.andExpect(jsonPath("$").value(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1StudentCountForATeacher_teacherId_notfound_zero_count_success() throws JsonProcessingException {
		
		testV1SubjectTeacherPost_success();

		try {
			mockMvc.perform(get("/v1/teachers/6767456/students/count")).andExpect(status().isOk())
					.andExpect(jsonPath("$").value(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectTeacherGetAll() throws JsonProcessingException {

		testV1SubjectTeacherPost_success();
		List<SubjectTeacherDto> teachersList = subjectTeacherService.getAllSubjectTeachers();

		try {
			mockMvc.perform(get("/v1/teachers/list")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].groupId", is(teachersList.get(0).getGroupId())))
					.andExpect(jsonPath("$[0].subjectId", is(teachersList.get(0).getSubjectId())))
					.andExpect(jsonPath("$[0].id", is(teachersList.get(0).getId())))
					.andExpect(jsonPath("$[0].id").isNotEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testV1SubjectTeacherDelete_Success() throws JsonProcessingException {
		testV1SubjectTeacherPost_success();

		List<SubjectTeacherDto> teachersList = subjectTeacherService.getAllSubjectTeachers();

		try {
			mockMvc.perform(delete("/v1/teachers/" + teachersList.get(0).getId())).andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testV1SubjectTeacherPut_Success() throws JsonProcessingException {
		testV1SubjectTeacherPost_success();

		List<SubjectTeacherDto> teachersList = subjectTeacherService.getAllSubjectTeachers();
		
		GroupDto groupDto = new GroupDto();
		groupDto.setName("Engineering");
		GroupDto groupResponse = groupService.addGroup(groupDto);
		Assertions.assertEquals("Engineering", groupResponse.getName());
		
		SubjectDto subjectDto =new SubjectDto();
		subjectDto.setTitle("English");
		SubjectDto subjectResponse = subjectService.addSubject(subjectDto);
		Assertions.assertEquals("English", subjectResponse.getTitle());
		
		SubjectTeacherDto teacherDto = new SubjectTeacherDto();
		teacherDto.setGroupId(groupResponse.getId());
		teacherDto.setSubjectId(subjectResponse.getId());
		teacherDto.setId(teachersList.get(0).getId());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDto);

		try {
			mockMvc.perform(put("/v1/teachers").contentType(MediaType.APPLICATION_JSON).content(requestJson))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
