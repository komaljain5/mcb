package com.nagaro.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagaro.sms.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
