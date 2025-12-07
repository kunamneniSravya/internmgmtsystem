package com.finalproject.internMgmtSystem.model;

import java.sql.Timestamp;

public class Trainer {

    private Long trainerId;
    private String name;
    private String email;
    private String password;
    private String contact;
    private String experience;
    private String skills;
 
    private String bio;
    private String role;
    private Timestamp createdAt;

    public Trainer() {}

    public Trainer(Long trainerId, String name, String email, String password, String contact, String experience, String skills,
                    String bio, String role, Timestamp createdAt) {

        this.trainerId = trainerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.experience = experience;
        this.skills = skills;
        
        this.bio = bio;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

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

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
