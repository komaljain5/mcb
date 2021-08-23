package com.nagaro.sms.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.nagaro.sms.dto.MarkDto;
import com.nagaro.sms.entity.Mark;

/**
 * This Converter class contains converter methods for Mark Entity and Dto beans.
 * @author komaljain01
 *
 */
@Component
public class MarkConverter implements Converter<Mark, MarkDto>{

	/**
	 * This method converts Mark to MarkDto
	 * @param mark
	 * @return markDto: Dto object mapped to Mark Entity
	 */
	public MarkDto convert(Mark mark) {
		MarkDto markDto = new MarkDto();
		BeanUtils.copyProperties(mark, markDto);
		markDto.setStudentId(mark.getStudent().getId());
		markDto.setSubjectId(mark.getSubject().getId());
		return markDto;
	}

}
