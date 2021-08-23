package com.nagaro.sms.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.nagaro.sms.converter.Converter;
import com.nagaro.sms.dto.SubjectTeacherDto;
import com.nagaro.sms.entity.Group;
import com.nagaro.sms.entity.Subject;
import com.nagaro.sms.entity.SubjectTeacher;
import com.nagaro.sms.exception.BadRequestException;
import com.nagaro.sms.exception.ResourceNotFoundException;
import com.nagaro.sms.repository.GroupRepository;
import com.nagaro.sms.repository.SubjectRepository;
import com.nagaro.sms.repository.SubjectTeacherRepository;
import com.nagaro.sms.service.SubjectTeacherService;
import com.nagaro.sms.util.Constants;
import com.nagaro.sms.validator.MandatoryFieldsValidator;

@Service
public class SubjectTeacherServiceImpl implements SubjectTeacherService {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectTeacherRepository subjectTeacherRepository;

	@Autowired
	MandatoryFieldsValidator fieldsValidator;
	
	@Autowired
	Converter<SubjectTeacher, SubjectTeacherDto> converter;

	@Override
	public SubjectTeacherDto addSubjectTeacher(SubjectTeacherDto subjectTeacherDto) {
		/* Validate the mandatory fields(studentId, subjectId) are present */
		fieldsValidator.validate(Arrays.asList(subjectTeacherDto.getGroupId(), subjectTeacherDto.getSubjectId()),
				Constants.subjectTeacherDtoCreate);

		/* verify if subject and group records exist with given subjectId and studentId */ 
		Optional<Group> existingGroupOptional = groupRepository.findById(subjectTeacherDto.getGroupId());
		if (!existingGroupOptional.isPresent()) {
			throw new ResourceNotFoundException(Constants.groupNotFound);
		}

		Optional<Subject> existingSubjectOptional = subjectRepository.findById(subjectTeacherDto.getSubjectId());
		if (!existingSubjectOptional.isPresent()) {
			throw new ResourceNotFoundException(Constants.subjectNotFound);
		}

		/* If valid student and subject records exist, save the SubjectTeacher */
		SubjectTeacher subjectTeacher = new SubjectTeacher();
		BeanUtils.copyProperties(subjectTeacherDto, subjectTeacher);
		subjectTeacher.setGroup(existingGroupOptional.get());
		subjectTeacher.setSubject(existingSubjectOptional.get());
		subjectTeacher = subjectTeacherRepository.save(subjectTeacher);
		BeanUtils.copyProperties(subjectTeacher, subjectTeacherDto);
		return subjectTeacherDto;

	}

	@Override
	public void updateSubjectTeacher(SubjectTeacherDto subjectTeacherDto) {
		if (ObjectUtils.isEmpty(subjectTeacherDto.getId()))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		/* Verify the studentId exists in system */
		Optional<SubjectTeacher> existingSubTeacherOptional = subjectTeacherRepository
				.findById(subjectTeacherDto.getId());
		if (!existingSubTeacherOptional.isPresent())
			throw new ResourceNotFoundException(Constants.studentNotFound);

		/* Update student details */
		SubjectTeacher existingSubTeacher = existingSubTeacherOptional.get();
		BeanUtils.copyProperties(subjectTeacherDto, existingSubTeacher);
		subjectTeacherRepository.save(existingSubTeacher);
	}

	@Override
	public void deleteSubjectTeacher(Integer subjectTeacherId) {
		if (ObjectUtils.isEmpty(subjectTeacherId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		Optional<SubjectTeacher> existingTeacherOptional = subjectTeacherRepository.findById(subjectTeacherId);
		if (existingTeacherOptional.isPresent())
			subjectTeacherRepository.deleteById(subjectTeacherId);
		else
			throw new ResourceNotFoundException(Constants.subjectTeacherNotFound);
	}

	@Override
	public List<SubjectTeacherDto> getAllSubjectTeachers() {
		List<SubjectTeacher> teacherList = subjectTeacherRepository.findAll();
		if (ObjectUtils.isEmpty(teacherList))
			throw new ResourceNotFoundException(Constants.noDataFound);

		List<SubjectTeacherDto> teacherDtoList = teacherList.stream()
															.map(converter::convert)
															.collect(Collectors.toList());
		return teacherDtoList;
	}

	@Override
	public SubjectTeacherDto getSubjectTeacherByTeacherId(Integer subjectTeacherId) {
		if (ObjectUtils.isEmpty(subjectTeacherId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		Optional<SubjectTeacher> existingTeacherOptional = subjectTeacherRepository.findById(subjectTeacherId);
		if (existingTeacherOptional.isPresent()) {
			return converter.convert(existingTeacherOptional.get());
		} else {
			throw new ResourceNotFoundException(Constants.subjectTeacherNotFound);
		}
	}

	/**
	 * This API returns the  number of students for a particular teacher id
	 * @param teacherId Integer : teacher whose students are to be fetched
	 * @return count : count of students of a SubjectTeacher
	 */
	@Override
	public Integer getStudentCountForSubjectTeacher(Integer subjectTeacherId) {
		if (ObjectUtils.isEmpty(subjectTeacherId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);
		
		return subjectTeacherRepository.getStudentCountForSubjectTeacher(subjectTeacherId);	
	}

}
