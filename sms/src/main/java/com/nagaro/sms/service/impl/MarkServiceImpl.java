package com.nagaro.sms.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.nagaro.sms.converter.Converter;
import com.nagaro.sms.dto.MarkDto;
import com.nagaro.sms.entity.Mark;
import com.nagaro.sms.entity.Student;
import com.nagaro.sms.entity.Subject;
import com.nagaro.sms.exception.BadRequestException;
import com.nagaro.sms.exception.ResourceNotFoundException;
import com.nagaro.sms.repository.MarkRepository;
import com.nagaro.sms.repository.StudentRepository;
import com.nagaro.sms.repository.SubjectRepository;
import com.nagaro.sms.service.MarkService;
import com.nagaro.sms.util.Constants;
import com.nagaro.sms.validator.MandatoryFieldsValidator;

@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	MarkRepository markRepository;

	@Autowired
	MandatoryFieldsValidator fieldsValidator;
	
	@Autowired
	@Qualifier("markConverter")
	Converter<Mark, MarkDto> converter;

	/**
	 * This API adds a new mark record for a given markId
	 * @param markId
	 * @return MarkDto
	 */
	@Override
	public MarkDto addMark(MarkDto markDto) {
		/* Validate the mandatory fields(studentId, subjectId) are present */
		fieldsValidator.validate(Arrays.asList(markDto.getStudentId(), markDto.getSubjectId()),
				Constants.markDtoCreate);

		/* verify if subject and student records exist with given subjectId and studentId */
		Optional<Student> existingStudentOptional = studentRepository.findById(markDto.getStudentId());
		if (!existingStudentOptional.isPresent()) {
			throw new ResourceNotFoundException(Constants.studentNotFound);
		}

		Optional<Subject> existingSubjectOptional = subjectRepository.findById(markDto.getSubjectId());
		if (!existingSubjectOptional.isPresent()) {
			throw new ResourceNotFoundException(Constants.subjectNotFound);
		}

		/* If valid student and subject records exist, save the Mark */
		Mark mark = new Mark();
		BeanUtils.copyProperties(markDto, mark);
		mark.setStudent(existingStudentOptional.get());
		mark.setSubject(existingSubjectOptional.get());
		mark = markRepository.save(mark);
		BeanUtils.copyProperties(mark, markDto);
		return markDto;
	}

	/**
	 * This API updates mark record for a given markId
	 * @param markId
	 */
	@Override
	public void updateMark(MarkDto markDto) {
		if (ObjectUtils.isEmpty(markDto.getId()))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		/* Verify the markId exists in system */
		Optional<Mark> existingMarkOptional = markRepository.findById(markDto.getId());
		if (!existingMarkOptional.isPresent()) {
			throw new ResourceNotFoundException(Constants.markNotFound);
		}

		/* Update Mark details */
		Mark existingMark = existingMarkOptional.get();
		BeanUtils.copyProperties(markDto, existingMark);
		markRepository.save(existingMark);
	}

	/**
	 * This API deletes mark record for a given markId
	 * @param markId
	 */
	@Override
	public void deleteMark(Integer markId) {
		if (ObjectUtils.isEmpty(markId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		Optional<Mark> existingMarkOtional = markRepository.findById(markId);
		if (existingMarkOtional.isPresent())
			markRepository.deleteById(markId);
		else
			throw new ResourceNotFoundException(Constants.markNotFound);

	}

	/**
	 * This API returns all mark records
	 * @param markId
	 * @return MarkDto
	 */
	@Override
	public List<MarkDto> getAllMarks() {
		List<Mark> markList = markRepository.findAll();
		if (ObjectUtils.isEmpty(markList))
			throw new ResourceNotFoundException(Constants.noDataFound);

		
		List<MarkDto> markDtoList = markList.stream()
											.map(converter:: convert)
											.collect(Collectors.toList());
		return markDtoList;
	}

	/**
	 * This API returns mark record for a given markId
	 * @param markId
	 * @return MarkDto
	 */
	@Override
	public MarkDto getMarkById(Integer markId) {
		if (ObjectUtils.isEmpty(markId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		Optional<Mark> existingMarkOptional = markRepository.findById(markId);
		if (existingMarkOptional.isPresent()) 
			return converter.convert(existingMarkOptional.get());
		else 
			throw new ResourceNotFoundException(Constants.markNotFound);
	}

	/**
	 * This API returns the marks for a particular StudentId
	 * 
	 * @param studentId Integer : student whose marks are to be fetched
	 * @return total : marks for a student
	 */
	@Override
	public Integer getMarkForStudentId(Integer studentId) {
		List<MarkDto> markList = this.getSubjectMarkListByStudentId(studentId);
		ToIntFunction<MarkDto> sum = m -> m.getMark();
		return markList.stream().collect(Collectors.summingInt(sum));
	}

	/**
	 * This API returns list of marks for each subject for a particular StudentId
	 * 
	 * @param studentId Integer : student whose marks are to be fetched
	 * @return total : marks for a student
	 */
	@Override
	public List<MarkDto> getSubjectMarkListByStudentId(Integer studentId) {
		if (ObjectUtils.isEmpty(studentId))
			throw new BadRequestException(Constants.genericRequiredFieldsMissingMsg);

		List<Mark> markList = markRepository.findByStudentId(studentId);
		if (ObjectUtils.isEmpty(markList))
			throw new ResourceNotFoundException(Constants.noDataFound);
		
		List<MarkDto> markDtoList = markList.stream()
											.map(converter:: convert)
											.collect(Collectors.toList());
		return markDtoList;
	}

}
