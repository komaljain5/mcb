package com.nagaro.sms.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MarkDto {
	
	@ApiModelProperty(notes = "To be entered for UPDATE operation only")
	private Integer id;

	@ApiModelProperty(required = true)
	private Integer studentId;

	@ApiModelProperty(required = true)
	private Integer subjectId;

	@ApiModelProperty(required = true)
	private Date date;

	@ApiModelProperty(required = true)
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
	 * @return the studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the subjectId
	 */
	public Integer getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
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
		return "MarkDto [id=" + id + ", studentId=" + studentId + ", subjectId=" + subjectId + ", date=" + date
				+ ", mark=" + mark + "]";
	}

}
