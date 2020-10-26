package com.sjsu.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Users {

	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private Date fileUploadTime;
    private Date fileModified;
    private String fileDesc;
    
    public Users() {
		// TODO Auto-generated constructor stub
	}
    
    
	public Users(String firstName, String lastName, Date fileUploadTime, Date fileModified, String fileDesc) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fileUploadTime = fileUploadTime;
		this.fileModified = fileModified;
		this.fileDesc = fileDesc;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getFileUploadTime() {
		return fileUploadTime;
	}
	public void setFileUploadTime(Date fileUploadTime) {
		this.fileUploadTime = fileUploadTime;
	}
	public Date getFileModified() {
		return fileModified;
	}
	public void setFileModified(Date fileModified) {
		this.fileModified = fileModified;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
    
    
}
