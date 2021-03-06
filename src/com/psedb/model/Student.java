package com.psedb.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

/**
 * Student generated by hbm2java
 */
public class Student implements java.io.Serializable {

	private Integer studentId;
	private String fname;
	private String surname;
	private String studentEmail;
	private String passwd;
	
	@Transient
	private Integer eid;

	public Student() {
	}

	public Student(String fname, String surname, String studentEmail,
			String passwd) {
		this.fname = fname;
		this.surname = surname;
		this.studentEmail = studentEmail;
		this.passwd = passwd;
	}

	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStudentEmail() {
		return this.studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

}
