package com.nagaro.sms.validator;

import java.util.List;

public interface MandatoryFieldsValidator {
	
	<T> void validate(List<T> fields, String operation);

}
