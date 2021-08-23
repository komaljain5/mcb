package com.nagaro.sms.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.nagaro.sms.dto.SubjectTeacherDto;
import com.nagaro.sms.entity.SubjectTeacher;

@Component
public class SubjectTeacherConverter implements Converter<SubjectTeacher, SubjectTeacherDto>{

	@Override
	public SubjectTeacherDto convert(SubjectTeacher teacher) {
		SubjectTeacherDto teacherDto = new SubjectTeacherDto();
		BeanUtils.copyProperties(teacher, teacherDto);
		teacherDto.setGroupId(teacher.getGroup().getId());
		teacherDto.setSubjectId(teacher.getSubject().getId());
		return teacherDto;
	}

}
