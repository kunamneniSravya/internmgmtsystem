package com.finalproject.internMgmtSystem.model;

import java.sql.Timestamp;

public class Stipend {

    private Long id;
    private Long userId;
    private String userName;
    private Timestamp paymentDate;
    private String paymentMode;
    private Double amount;

    public Stipend() {}

    public Stipend(Long id, Long userId, String userName, Timestamp paymentDate, String paymentMode, Double amount) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.amount = amount;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
