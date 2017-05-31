package com.psedb.model;

public class Assessment {
	private Integer aid;
	private Student student;
	private Enrollment enrollment;
	private Integer a1;
	private Integer a2;
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Enrollment getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	public Integer getA1() {
		return a1;
	}
	public void setA1(Integer a1) {
		this.a1 = a1;
	}
	public Integer getA2() {
		return a2;
	}
	public void setA2(Integer a2) {
		this.a2 = a2;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
}
