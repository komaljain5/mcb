package com.nagaro.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GroupDto {

	@ApiModelProperty(notes="To be entered for UPDATE operation only")
	private Integer id;

	@ApiModelProperty(required=true, notes="Name of the group")
	private String name;

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

	@Override
	public String toString() {
		return "GroupDto [id=" + id + ", name=" + name + "]";
	}

}
