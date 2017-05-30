package com.psedb.model;

public class Enrollment implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer eid;
	private String semester;
	private Student student;
	private Course course;
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	} 
	
	

}
