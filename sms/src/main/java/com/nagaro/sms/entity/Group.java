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

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "group", cascade=CascadeType.REMOVE)
	List<Student> students = new ArrayList<>();

	@OneToMany(mappedBy = "group", cascade=CascadeType.REMOVE)
	List<SubjectTeacher> teachers = new ArrayList<>();

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<Student> students) {
		this.students = students;
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
		return "Group [id=" + id + ", name=" + name + ", students=" + students + ", teachers=" + teachers + "]";
	}

}
