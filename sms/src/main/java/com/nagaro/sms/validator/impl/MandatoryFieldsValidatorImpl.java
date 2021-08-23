package com.nagaro.sms.validator.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.nagaro.sms.exception.RequiredFieldsMissingException;
import com.nagaro.sms.factory.ValidatorMessageFactory;
import com.nagaro.sms.validator.MandatoryFieldsValidator;

@Component
public class MandatoryFieldsValidatorImpl implements MandatoryFieldsValidator {

	/**
	 * @author komaljain01
	 * @param fields: List of fields to be validated
	 * @param type	: The dto operation for which fields are being validated
	 */
	@Override
	public <T> void validate(List<T> fields, String operation) {
		
		fields.forEach(field -> {
			if ((field instanceof String && !StringUtils.hasText((String) field))
					|| (!(field instanceof String) && ObjectUtils.isEmpty(field)))
				throw new RequiredFieldsMissingException(ValidatorMessageFactory.getValidationMessage(operation));
		});
	}

}
