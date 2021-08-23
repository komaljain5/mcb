package com.nagaro.sms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.nagaro.sms.dto.SubjectDto;
import com.nagaro.sms.entity.Subject;
import com.nagaro.sms.exception.ResourceNotFoundException;
import com.nagaro.sms.repository.SubjectRepository;
import com.nagaro.sms.service.SubjectService;
import com.nagaro.sms.util.Constants;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	SubjectRepository subjectRepository;

	@Override
	public SubjectDto addSubject(SubjectDto subjectDto) {
		Subject subject = new Subject();
		BeanUtils.copyProperties(subjectDto, subject);
		subject = subjectRepository.save(subject);
		BeanUtils.copyProperties(subject, subjectDto);
		return subjectDto;
	}

	@Override
	public void updateSubject(SubjectDto subjectDto) {
		Optional<Subject> existingSubject = subjectRepository.findById(subjectDto.getId());
		if (existingSubject.isPresent()) {
			Subject subject = existingSubject.get();
			BeanUtils.copyProperties(subjectDto, subject);
			subjectRepository.save(subject);
		} else {
			throw new ResourceNotFoundException(Constants.subjectNotFound);
		}
	}

	@Override
	public void deleteSubject(Integer subjectId) {
		Optional<Subject> existingSubject = subjectRepository.findById(subjectId);
		if (existingSubject.isPresent())
			subjectRepository.deleteById(subjectId);
		else
			throw new ResourceNotFoundException(Constants.subjectNotFound);
	}

	@Override
	public List<SubjectDto> getAllSubjects() {
		List<Subject> subjectList = subjectRepository.findAll();
		if (ObjectUtils.isEmpty(subjectList)) {
			throw new ResourceNotFoundException(Constants.noDataFound);
		}

		List<SubjectDto> subjectDtoList = new ArrayList<>();
		for (Subject subject : subjectList) {
			SubjectDto subjectDto = new SubjectDto();
			BeanUtils.copyProperties(subject, subjectDto);
			subjectDtoList.add(subjectDto);
		}
		return subjectDtoList;
	}

	@Override
	public SubjectDto getSubjectById(Integer subjectId) {
		SubjectDto subjectDto = new SubjectDto();
		Optional<Subject> existingSubject = subjectRepository.findById(subjectId);
		if (existingSubject.isPresent()) {
			BeanUtils.copyProperties(existingSubject.get(), subjectDto);
			return subjectDto;
		} else {
			throw new ResourceNotFoundException(Constants.subjectNotFound);
		}
	}

}
