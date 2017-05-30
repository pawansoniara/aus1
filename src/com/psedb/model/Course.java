package com.psedb.model;

 
public class Course implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Byte cid;
	private String description;
	
	public Course(){}
	
	public Course(Byte cid,String description) {
		this.cid=cid;
		this.description=description;
	}
	public Byte getCid() {
		return cid;
	}
	public void setCid(Byte cid) {
		this.cid = cid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	

}
