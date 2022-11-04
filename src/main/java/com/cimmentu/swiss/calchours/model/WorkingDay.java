package com.cimmentu.swiss.calchours.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class WorkingDay implements Serializable{
	
	private static final long serialVersionUID = -5987412000897830175L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private int userId;
	
	private String workType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate workDate; 
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startHour;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endHour;

	public LocalDate getWorkDate() {
		return workDate;
	}

	public void setWorkDate(LocalDate localDate) {
		this.workDate = localDate;
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	@Override
	public String toString() {
		return "WorkingDay [id=" + id + ", userId=" + userId + ", workType=" + workType + ", workDate=" + workDate
				+ ", startHour=" + startHour + ", endHour=" + endHour + "]";
	}
	
}
