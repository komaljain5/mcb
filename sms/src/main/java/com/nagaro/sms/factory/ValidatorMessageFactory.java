package com.nagaro.sms.factory;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nagaro.sms.util.Constants;

@Component
public class ValidatorMessageFactory {

	private static HashMap<String, String> messageMap;

	public static String getValidationMessage(String operation) {
		return StringUtils.hasText(operation) ? messageMap.get(operation) : Constants.genericErrorMessage;
	}

	static {
		messageMap = new HashMap<>();
		messageMap.put(Constants.studentDtoOps, Constants.genericRequiredFieldsMissingMsg);
		messageMap.put(Constants.studentDtoUpdate, Constants.studentDtoUpdateRequiredFieldsMsg);
		messageMap.put(Constants.studentDtoCreate, Constants.studentDtoCreateRequiredFieldsMsg);
		messageMap.put(Constants.markDtoCreate, Constants.markDtoCreateRequiredFieldsMsg);
		messageMap.put(Constants.subjectTeacherDtoCreate, Constants.subjectTeacherDtoRequiredFieldsMsg);
	}

}
