package com.nagaro.sms.util;

public class Constants {
	
	/* API response status code message */
	public static final String created = "Created";
	public static final String updated = "Updated";
	public static final String deleted = "Deleted";

	/* Authentication & Authorization Error messages */
	public static final String authenticationFailed = "Unauthorized to access the resource";
	public static final String authorizationFailed = "You don't have necessary permissions to access the requested resource";
	public static final String tokenExpired = "Token has expired.Please try with a new token";

	/* Error messages for RResourceNotFoundException */
	public static final String noDataFound = "No Data found for your request";
	public static final String genericErrorMessage = "Unable to process your request at this time. Please try again after sometime";

	public static final String groupNotFound = "No group found for the given id";
	public static final String studentNotFound = "No student found for the given id";
	public static final String markNotFound = "No mark found for the given id";
	public static final String subjectNotFound = "No subject found for the given id";
	public static final String subjectTeacherNotFound = "No subject teacher found for the given id";

	/* Error messages for RequiredFieldsMissingException */
	public static final String genericRequiredFieldsMissingMsg = "id is mandatory field";
	public static final String studentDtoCreateRequiredFieldsMsg = "groupId is a mandatory field";
	public static final String studentDtoUpdateRequiredFieldsMsg = "id and groupId are mandatory fields";

	public static final String markDtoCreateRequiredFieldsMsg = "studentId and subjectId are mandatory fields";
	public static final String subjectTeacherDtoRequiredFieldsMsg = "groupId and subjectId are mandatory fields";

	/* Dto Operation Constants for ValidationMessageFactory */
	public static final String studentDtoOps = "studentdto.other";
	public static final String studentDtoCreate = "studentdto.create";
	public static final String studentDtoUpdate = "studentdto.update";
	public static final String markDtoCreate = "markdto.create";
	public static final String subjectTeacherDtoCreate = "subjectteacherdto.create";


}
