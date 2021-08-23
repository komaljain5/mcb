package com.nagaro.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

/**
 * This entity maps to marks table. 
 * ASSUMPTION: A student will have a single mark record for a given subject
 * 
 * @author komaljain01
 *
 */
@Entity
@Table(name = "marks", uniqueConstraints = {
		@UniqueConstraint(name = "UniqueStudentAndSubject", columnNames = { "student_id", "subject_id" }) })
public class Mark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mark_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	@NotNull
	private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	@NotNull
	private Subject subject;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private Integer mark;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the mark
	 */
	public Integer getMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(Integer mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "Mark [id=" + id + ", student=" + student + ", subject=" + subject + ", date=" + date + ", mark=" + mark
				+ "]";
	}

}
