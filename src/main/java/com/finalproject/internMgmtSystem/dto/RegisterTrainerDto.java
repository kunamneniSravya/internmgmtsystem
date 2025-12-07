package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;

public class RegisterTrainerDto {

   
	@NotBlank(message = "Trainer name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Contact number is required")
    private String contact;

    @NotBlank(message = "Experience field is required")
    private String experience;

    @NotBlank(message = "Skills field is required")
    private String skills;

 

    @NotBlank(message = "Bio is required")
    private String bio;

    public RegisterTrainerDto() {}
    public RegisterTrainerDto(@NotBlank(message = "Trainer name is required") String name,
			@NotBlank(message = "Email is required") @Email(message = "Enter valid email") String email,
			@NotBlank(message = "Password is required") String password,
			@NotBlank(message = "Contact number is required") String contact,
			@NotBlank(message = "Experience field is required") String experience,
			@NotBlank(message = "Skills field is required") String skills, 
			@NotBlank(message = "Bio is required") String bio) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.experience = experience;
		this.skills = skills;
	
		this.bio = bio;
	}
    // getters + setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }



    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
