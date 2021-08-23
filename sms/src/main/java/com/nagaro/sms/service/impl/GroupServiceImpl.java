package com.nagaro.sms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.nagaro.sms.dto.GroupDto;
import com.nagaro.sms.entity.Group;
import com.nagaro.sms.exception.BadRequestException;
import com.nagaro.sms.exception.ResourceNotFoundException;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.service.GroupService;
import com.nagaro.sms.util.Constants;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	GroupRepository groupRepository;

	 /**
     * Method creates a group entity in system
     * @param groupDTO
     * @return GroupDto : GroupDto populated with newly created entity details
     */
	@Override
	public GroupDto addGroup(GroupDto groupDto) {
		Group group = new Group();
		BeanUtils.copyProperties(groupDto, group);
		group = groupRepository.save(group);
		BeanUtils.copyProperties(group, groupDto);
		return groupDto;
	}

	 /**
     * Method updates a group entity in system
     * @param groupDTO
     * @return 
     */
	@Override
	public void updateGroup(GroupDto groupDto) {
		if (ObjectUtils.isEmpty(groupDto.getId()))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);
		
		Optional<Group> existingGroup = groupRepository.findById(groupDto.getId());
		if (existingGroup.isPresent()) {
			Group group = existingGroup.get();
			BeanUtils.copyProperties(groupDto, group);
			groupRepository.save(group);
		} else {
			throw new ResourceNotFoundException(Constants.groupNotFound);
		}
	}

	 /**
     * Method deletes a group entity in system
     * @param groupDTO
     * @return GroupDto : GroupDto populated with newly created entity details
     */
	@Override
	public void deleteGroup(Integer groupId) {
		Optional<Group> existingGroup = groupRepository.findById(groupId);
		if (existingGroup.isPresent())
			groupRepository.deleteById(groupId);
		else
			throw new ResourceNotFoundException(Constants.groupNotFound);
	}

	@Override
	public List<GroupDto> getAllGroups() {
		List<GroupDto> groupDtoList = new ArrayList<>();
		List<Group> groupList = groupRepository.findAll();
		if (ObjectUtils.isEmpty(groupList)) {
			throw new ResourceNotFoundException(Constants.noDataFound);
		}

		for (Group group : groupList) {
			GroupDto groupDto = new GroupDto();
			BeanUtils.copyProperties(group, groupDto);
			groupDtoList.add(groupDto);
		}
		return groupDtoList;
	}

	@Override
	public GroupDto getGroupById(Integer groupId) {
		GroupDto groupDto = new GroupDto();
		Optional<Group> existingGroup = groupRepository.findById(groupId);
		if (existingGroup.isPresent()) {
			BeanUtils.copyProperties(existingGroup.get(), groupDto);
			return groupDto;
		} else {
			throw new ResourceNotFoundException(Constants.groupNotFound);
		}
	}

}
