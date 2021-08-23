package com.nagaro.sms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "subjects")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subject_id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@OneToMany(mappedBy = "subject")
	@NotNull
	private List<Mark> marks;

	@OneToMany(mappedBy = "subject", cascade=CascadeType.REMOVE)
	@NotNull
	private List<SubjectTeacher> teachers = new ArrayList<>();

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the marks
	 */
	public List<Mark> getMarks() {
		return marks;
	}

	/**
	 * @param marks the marks to set
	 */
	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

	/**
	 * @return the teachers
	 */
	public List<SubjectTeacher> getTeachers() {
		return teachers;
	}

	/**
	 * @param teachers the teachers to set
	 */
	public void setTeachers(List<SubjectTeacher> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", title=" + title + ", marks=" + marks + ", teachers=" + teachers + "]";
	}

}
