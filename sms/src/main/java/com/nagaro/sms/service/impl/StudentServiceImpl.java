package com.nagaro.sms.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.nagaro.sms.converter.Converter;
import com.nagaro.sms.dto.StudentDto;
import com.nagaro.sms.entity.Group;
import com.nagaro.sms.entity.Student;
import com.nagaro.sms.exception.BadRequestException;
import com.nagaro.sms.exception.ResourceNotFoundException;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.repository.StudentRepository;
import com.nagaro.sms.service.StudentService;
import com.nagaro.sms.util.Constants;
import com.nagaro.sms.validator.MandatoryFieldsValidator;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	MandatoryFieldsValidator fieldsValidator;
	
	@Autowired
	Converter<Student, StudentDto> converter;

	/**
	 * This API creates new student entity in system
	 * 
	 * @param studentDTO
	 * @return StudentDto object of saved Student entity.
	 */
	public StudentDto addStudent(StudentDto studentDto) {
		/* Validate the mandatory fields(groupId) are present */
		fieldsValidator.validate(Arrays.asList(studentDto.getGroupId()), Constants.studentDtoCreate);

		/* check if a group exists for the given group id */
		Optional<Group> existingGroup = groupRepository.findById(studentDto.getGroupId());
		if (!existingGroup.isPresent()) {
			throw new ResourceNotFoundException(Constants.groupNotFound);
		}
		/* If a valid group exists, save the student */
		Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);
		student.setGroup(existingGroup.get());
		student = studentRepository.save(student);
		BeanUtils.copyProperties(student, studentDto);
		return studentDto;
	}

	/**
	 * This API updates existing entity detail
	 * 
	 * @param studentDTO object
	 */
	public void updateStudent(StudentDto studentDto) {
		if (ObjectUtils.isEmpty(studentDto.getId()))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		/* Verify the studentId exists in system */
		Optional<Student> existingStudentOptional = studentRepository.findById(studentDto.getId());
		if (!existingStudentOptional.isPresent())
			throw new ResourceNotFoundException(Constants.studentNotFound);

		/* Update student details */
		Student existingStudent = existingStudentOptional.get();
		BeanUtils.copyProperties(studentDto, existingStudent);
		studentRepository.save(existingStudent);
	}

	/**
	 * This API deletes Student entity based on id
	 * 
	 * @param studentId
	 */
	public void deleteStudent(Integer studentId) {
		if (ObjectUtils.isEmpty(studentId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		Optional<Student> existingSubject = studentRepository.findById(studentId);
		if (existingSubject.isPresent())
			studentRepository.deleteById(studentId);
		else
			throw new ResourceNotFoundException(Constants.studentNotFound);
	}

	/**
	 * This API fetches List of all available Student entity records
	 * 
	 * @return List<StudentDto>
	 */
	public List<StudentDto> getAllStudents() {
		List<Student> studentList = studentRepository.findAll();
		if (ObjectUtils.isEmpty(studentList))
			throw new ResourceNotFoundException(Constants.noDataFound);

		List<StudentDto> studentDtoList = studentList.stream()
													 .map(converter::convert)
													 .collect(Collectors.toList());		
		return studentDtoList;
	}

	/**
	 * This API finds an existing Student entity based on studentId
	 * 
	 * @param studentDTO object
	 * @return StudentDto object mapped from the fetched entity object
	 */
	public StudentDto getStudentById(Integer studentId) {
		if (ObjectUtils.isEmpty(studentId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		Optional<Student> existingStudentOptional = studentRepository.findById(studentId);
		if (existingStudentOptional.isPresent()) {
			return converter.convert(existingStudentOptional.get());
		} else {
			throw new ResourceNotFoundException(Constants.studentNotFound);
		}
	}

}
