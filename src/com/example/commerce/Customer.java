package com.example.commerce;

public class Customer {
    // 필드
    private String customerName;
    private String email;
    private String rank;

    // 생성자
    public Customer(String customerName, String email, String rank) {
        this.customerName = customerName;
        this.email = email;
        this.rank = rank;
    }

    // Getter, Setter
    public String getCustomerName() {
        return customerName;
    }
    public String getEmail() {
        return email;
    }
    public String getRank() {
        return rank;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
}
