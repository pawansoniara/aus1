package com.psedb.model;
public class StudentSupervisor implements java.io.Serializable {

	private Integer supervisorId;
	private Staff staff;
	private StudentDegree studentDegree;
	private String supervisorType;

	public StudentSupervisor() {
	}

	public StudentSupervisor(Staff staff, StudentDegree studentDegree) {
		this.staff = staff;
		this.studentDegree = studentDegree;
	}

	public StudentSupervisor(Staff staff, StudentDegree studentDegree,
			String supervisorType) {
		this.staff = staff;
		this.studentDegree = studentDegree;
		this.supervisorType = supervisorType;
	}

	public Integer getSupervisorId() {
		return this.supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public StudentDegree getStudentDegree() {
		return this.studentDegree;
	}

	public void setStudentDegree(StudentDegree studentDegree) {
		this.studentDegree = studentDegree;
	}

	public String getSupervisorType() {
		return this.supervisorType;
	}

	public void setSupervisorType(String supervisorType) {
		this.supervisorType = supervisorType;
	}

}
