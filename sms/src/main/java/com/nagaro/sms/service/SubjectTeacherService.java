package com.nagaro.sms.service;

import java.util.List;

import com.nagaro.sms.dto.SubjectTeacherDto;

public interface SubjectTeacherService {
	
	SubjectTeacherDto addSubjectTeacher(SubjectTeacherDto subjectTeacherDto);
	
	void updateSubjectTeacher(SubjectTeacherDto subjectTeacherDto);

	void deleteSubjectTeacher(Integer subjectTeacherId);

    List<SubjectTeacherDto> getAllSubjectTeachers();
    
    SubjectTeacherDto getSubjectTeacherByTeacherId(Integer teacherId);

	/**
	 * This API returns the  number of students for a particular teacher id
	 * @param teacherId Integer : teacher whose students are to be fetched
	 * @return count : count of students of a SubjectTeacher
	 */
	Integer getStudentCountForSubjectTeacher(Integer subjectTeacherId);
    
}
