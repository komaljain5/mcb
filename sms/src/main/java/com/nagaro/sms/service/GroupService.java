package com.nagaro.sms.service;

import java.util.List;

import com.nagaro.sms.dto.GroupDto;

public interface GroupService {
	
	GroupDto addGroup(GroupDto groupDto);
	
	void updateGroup(GroupDto groupDto);

	void deleteGroup(Integer groupId);
	
	GroupDto getGroupById(Integer groupId);

    List<GroupDto> getAllGroups();
    
}
