package com.nagaro.sms.service;

import java.util.List;

import com.nagaro.sms.dto.StudentDto;

public interface StudentService {
	
	StudentDto addStudent(StudentDto studentDto);

    void updateStudent(StudentDto studentDto);

    void deleteStudent(Integer studentId);

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Integer studentId);

}
