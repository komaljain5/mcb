package com.nagaro.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagaro.sms.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{

}