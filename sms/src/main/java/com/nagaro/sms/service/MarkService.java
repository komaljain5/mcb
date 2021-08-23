package com.nagaro.sms.service;

import java.util.List;

import com.nagaro.sms.dto.MarkDto;

public interface MarkService {

	MarkDto addMark(MarkDto markDto);
	
	void updateMark(MarkDto markDto);

	void deleteMark(Integer markId);

    List<MarkDto> getAllMarks();
    
    MarkDto getMarkById(Integer markId);

    Integer getMarkForStudentId(Integer studentId);
    
  	List<MarkDto> getSubjectMarkListByStudentId(Integer studentId);

	
   
}
