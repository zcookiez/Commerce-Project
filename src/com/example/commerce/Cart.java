package com.example.commerce;

public class Cart {
    private String productName;  // 상품명
    private int price;           // 상품 가격 (질문하신 개당 가격!)
    private int quantity;        // 수량

    // 생성자
    public Cart(String productName, int price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter
    public String getProductName() {
        return productName;
    }
    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    // 수량이 추가될 때 기존 수량에 누적해주는 메서드
    public void addQuantity(int count) {
        this.quantity += count;
    }

    @Override
    public String toString() {
        int itemTotalPrice = this.price * this.quantity;
        return String.format("%-15s | %,10d원 | %5d개 | %,12d원",
                productName, price, quantity, itemTotalPrice);
    }


}
