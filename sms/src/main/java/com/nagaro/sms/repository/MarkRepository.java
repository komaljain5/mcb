package com.nagaro.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagaro.sms.entity.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer>{

	List<Mark> findByStudentId(Integer studentId);

}
