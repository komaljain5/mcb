package com.nagaro.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SubjectTeacherDto {
	
	@ApiModelProperty(notes = "To be entered for UPDATE operation only")
	private Integer id;

	@ApiModelProperty(required = true)
	private Integer subjectId;

	@ApiModelProperty(required = true)
	private Integer groupId;

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
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "SubjectTeacherDto [id=" + id + ", subjectId=" + subjectId + ", groupId=" + groupId + "]";
	}
	
}
