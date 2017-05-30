package com.psedb.model;

 
public class CourseConduction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Byte ccid;
	private String semester;
	private Staff staff;
	private Course course;
	
	
	public Byte getCcid() {
		return ccid;
	}
	public void setCcid(Byte ccid) {
		this.ccid = ccid;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	

	

}
