package com.psedb.model;
import java.util.HashSet;
import java.util.Set;

/**
 * Staff generated by hbm2java
 */
public class Staff implements java.io.Serializable {

	private Byte staffId;
	private LuAccessLevel luAccessLevel;
	private String fname;
	private String surname;
	private String loginname;
	private String passwd;
	private Set studentComments = new HashSet(0);
	private Set studentSupervisors = new HashSet(0);

	public Staff() {
	}

	public Staff(LuAccessLevel luAccessLevel, String fname, String surname,
			String loginname, String passwd, Set studentComments,
			Set studentSupervisors) {
		this.luAccessLevel = luAccessLevel;
		this.fname = fname;
		this.surname = surname;
		this.loginname = loginname;
		this.passwd = passwd;
		this.studentComments = studentComments;
		this.studentSupervisors = studentSupervisors;
	}
        
        public Staff(LuAccessLevel luAccessLevel, String fname, String surname,
			String loginname, String passwd) {
		this.luAccessLevel = luAccessLevel;
		this.fname = fname;
		this.surname = surname;
		this.loginname = loginname;
		this.passwd = passwd;
	}
        
        public Staff(LuAccessLevel luAccessLevel,Byte staffId, String fname, String surname,
			String loginname) {
		this.luAccessLevel = luAccessLevel;
		this.staffId=staffId;
                this.fname = fname;
		this.surname = surname;
		this.loginname = loginname;
	}

	public Byte getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Byte staffId) {
		this.staffId = staffId;
	}

	public LuAccessLevel getLuAccessLevel() {
		return this.luAccessLevel;
	}

	public void setLuAccessLevel(LuAccessLevel luAccessLevel) {
		this.luAccessLevel = luAccessLevel;
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

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Set getStudentComments() {
		return this.studentComments;
	}

	public void setStudentComments(Set studentComments) {
		this.studentComments = studentComments;
	}

	public Set getStudentSupervisors() {
		return this.studentSupervisors;
	}

	public void setStudentSupervisors(Set studentSupervisors) {
		this.studentSupervisors = studentSupervisors;
	}

}
