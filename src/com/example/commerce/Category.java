package com.example.commerce;

import java.util.List;

public class Category implements Printable{

    // 필드
    private String category; // 카테고리명
    private List<Product> products; // 상품 리스트


    // 생성자
    public Category( String category,List<Product> products){
        this.category = category;
        this.products = products;
    }

    // Getter, Setter
    public List<Product> getProducts() {
        return products;
    }

    public String getCategory() {
        return category;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    @Override
    public String printFormat() {
        return this.category;
    }
}
