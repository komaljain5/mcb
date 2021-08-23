package com.nagaro.sms.service;

import java.util.List;

import com.nagaro.sms.dto.SubjectDto;

public interface SubjectService {
	
	SubjectDto addSubject(SubjectDto SubjectDto);
	
	void updateSubject(SubjectDto SubjectDto);

	void deleteSubject(Integer SubjectId);

    List<SubjectDto> getAllSubjects();
    
    SubjectDto getSubjectById(Integer SubjectId);

}
