package com.nagaro.sms.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.nagaro.sms.dto.StudentDto;
import com.nagaro.sms.entity.Student;

@Component
public class StudentConverter implements Converter<Student, StudentDto> {

	@Override
	public StudentDto convert(Student student) {
		StudentDto studentDto = new StudentDto();
		BeanUtils.copyProperties(student, studentDto);
		studentDto.setGroupId(student.getGroup().getId());
		return studentDto;
	}

}
