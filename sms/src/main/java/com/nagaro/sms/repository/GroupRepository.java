package com.nagaro.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagaro.sms.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer>{

}
