package com.nagaro.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SubjectDto {

	@ApiModelProperty(notes = "To be entered for UPDATE operation only")
	private Integer id;

	@ApiModelProperty(required = true)
	private String title;

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

	@Override
	public String toString() {
		return "SubjectDto [id=" + id + ", title=" + title + "]";
	}

}
