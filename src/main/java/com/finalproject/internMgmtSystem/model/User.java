package com.finalproject.internMgmtSystem.model;

import java.sql.Date;
import java.sql.Timestamp;

public class User {

    
	private Long userId;
    private String userName;
    private String email;
    private String password;
    private Date dob;
    private String contactNo;
    private String address;
    private String collegeName;
    private String grade;
    private String major;
    private String team;
    private Long batchCode;
    private Date startDate;
    private Date endDate;
    private Integer graduatingYear;
    private String resume;
//    private String profilePic;
    private String role;
    private Timestamp createdAt;
    


    public User() {}
    public User(Long userId, String userName, String email, String password, Date dob, String contactNo, String address,
			String collegeName, String grade, String major, String team, Long batchCode, Date startDate, Date endDate,
			Integer graduatingYear, String resume,  String role, Timestamp createdAt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.contactNo = contactNo;
		this.address = address;
		this.collegeName = collegeName;
		this.grade = grade;
		this.major = major;
		this.team = team;
		this.batchCode = batchCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.graduatingYear = graduatingYear;
		this.resume = resume;
//		this.profilePic = profilePic;
		this.role = role;
		this.createdAt = createdAt;
	}
   

    // Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Integer getGraduatingYear() { return graduatingYear; }
    public void setGraduatingYear(Integer graduatingYear) { this.graduatingYear = graduatingYear; }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

//    public String getProfilePic() { return profilePic; }
//    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
	public Long getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(Long batchCode) {
		this.batchCode = batchCode;
	}
}
