package com.nagaro.sms.dao;

public interface SMSDao {
	/**
	 * Returns the  number of students for a particular teacher id
	 * @param subjectTeacherId
	 * @return count : total student count for given teacher id
	 */
	Integer getStudentCountForSubjectTeacher(Integer subjectTeacherId);

}
