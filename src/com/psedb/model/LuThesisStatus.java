package com.psedb.model;
import java.util.HashSet;
import java.util.Set;

/**
 * LuThesisStatus generated by hbm2java
 */
public class LuThesisStatus implements java.io.Serializable {

	private Byte thesisStatusId;
	private String status;
	private Byte displayOrder;
	private Set studentDegrees = new HashSet(0);

	public LuThesisStatus() {
	}

	public LuThesisStatus(String status, Byte displayOrder, Set studentDegrees) {
		this.status = status;
		this.displayOrder = displayOrder;
		this.studentDegrees = studentDegrees;
	}

	public Byte getThesisStatusId() {
		return this.thesisStatusId;
	}

	public void setThesisStatusId(Byte thesisStatusId) {
		this.thesisStatusId = thesisStatusId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Byte getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Byte displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Set getStudentDegrees() {
		return this.studentDegrees;
	}

	public void setStudentDegrees(Set studentDegrees) {
		this.studentDegrees = studentDegrees;
	}

}
