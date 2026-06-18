package com.example.commerce;

public class Customer {
    // 필드
    private String customerName;
    private String email;
    private int rank;

    // 생성자
    public Customer(String customerName, String email, int rank) {
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
    public int getRank() {
        return rank;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
}
