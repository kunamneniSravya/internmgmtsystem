package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;
import java.sql.Date;

public class RegisterUserDto {

    
	@NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Date of birth is required")
    private Date dob;

    @NotBlank(message = "Contact number is required")
    private String contactNo;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "College name is required")
    private String collegeName;

    @NotBlank(message = "Grade is required")
    private String grade;

    @NotBlank(message = "Major is required")
    private String major;

    @NotBlank(message = "Team selection is required")
    private String team;
    
    @NotNull(message = "batch Code is required")
    private Long batchCode;
    		
    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotNull(message = "Graduating year is required")
    @Min(value = 2020, message = "Graduating year must be >= 2020")
    private Integer graduatingYear;

    @NotBlank(message = "Resume URL/path is required")
    private String resume;

//    private String profilePic;

    public RegisterUserDto() {}
    
    
    public RegisterUserDto(@NotBlank(message = "User name is required") String userName,
			@NotBlank(message = "Email is required") @Email(message = "Enter a valid email") String email,
			@NotBlank(message = "Password is required") String password,
			@NotNull(message = "Date of birth is required") Date dob,
			@NotBlank(message = "Contact number is required") String contactNo,
			@NotBlank(message = "Address is required") String address,
			@NotBlank(message = "College name is required") String collegeName,
			@NotBlank(message = "Grade is required") String grade,
			@NotBlank(message = "Major is required") String major,
			@NotBlank(message = "Team selection is required") String team,
			@NotBlank(message = "batch Code selection is required") Long batchCode,
			@NotNull(message = "Start date is required") Date startDate,
			@NotNull(message = "End date is required") Date endDate,
			@NotNull(message = "Graduating year is required") @Min(value = 2020, message = "Graduating year must be >= 2020") Integer graduatingYear,
			@NotBlank(message = "Resume URL/path is required") String resume) {
		super();
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
	}

    // constructor + getters + setters
    // ... (same as previous but unchanged)
    
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


	public Long getBatchCode() {
		return batchCode;
	}


	public void setBatchCode(Long batchCode) {
		this.batchCode = batchCode;
	}
}
