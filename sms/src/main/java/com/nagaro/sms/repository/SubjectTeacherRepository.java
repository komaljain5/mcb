package com.nagaro.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nagaro.sms.entity.SubjectTeacher;

public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher, Integer>{
	/**
	 * Returns the  number of students for a particular teacher id
	 * @param subjectTeacherId
	 * @return count : total student count for given teacher id
	 */
	@Query(value = "select COUNT(s.student_id) from students s join marks m\r\n"
			+ "on s.student_id = m.student_id join subjects sub\r\n"
			+ "on m.subject_id = sub.subject_id join subject_teacher t\r\n"
			+ "on sub.subject_id = t.subject_id\r\n"
			+ "where t.group_id = s.group_id\r\n"
			+ "and t.teacher_id =:subjectTeacherId", nativeQuery=true)
	Integer getStudentCountForSubjectTeacher(Integer subjectTeacherId);

}
