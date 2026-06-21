package com.example.commerce;

import java.text.DecimalFormat;

public class Product {
    // 필드
    private String name;        // 상품명
    private int price;          // 가격
    private String description; // 설명
    private int stockQuantity;  // 재고수량


    // 모든 필드를 초기화하는 생성자
    public Product(String name, int price, String description, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    // Getter, Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        if (price < 0) { // 가격은 음수가 될 수 없다.
            this.price = 0;
        } else {
            this.price = price;
        }
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) { // 재고는 음수가 될 수 없다.
            this.stockQuantity = 0;
        } else {
            this.stockQuantity = stockQuantity;
        }
    }

    /*상품 출력*/
    @Override
    public String toString() {
        // 1. 가격을 천 단위 쉼표 형태로 포맷팅 (예: 1200000 -> 1,200,000)
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedPrice = df.format(price) + "원";

        // 2. 원하는 형태로 문자열 조합 후 반환
        // %-10s : 상품명을 왼쪽 정렬하고 10의 공간을 확보 (글자 길이에 따라 숫자 조절 가능)
        // %-10s : 포맷팅된 가격을 왼쪽 정렬하고 10칸의 공간을 확보
        return String.format("%-10s | %-10s | %s", name, formattedPrice, description);
    }

    /*상품 상세 출력*/
    public String printDetail() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n==============================================\n");
        sb.append("🔍 [상품 상세 정보]\n");
        sb.append("- 🔖상품명 : ").append(this.name).append("\n");
        sb.append("- 💵가격   : ").append(String.format("%,d원", this.price)).append("\n");
        sb.append("- 📝설명   : ").append(this.description).append("\n");
        sb.append("- 📦재고   : ").append(this.stockQuantity).append("개\n");
        sb.append("==============================================");


        return sb.toString(); // 완성된 하나의 전체 문자열을 반환
    }


}