package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 상품 및 카테고리 생성
        // 1. 전자제품
        List<Product> electronicProducts = new ArrayList<>();
        electronicProducts.add(new Product("맥북 에어", 1390000, "M3 가벼운 노트북", 10));
        electronicProducts.add(new Product("아이폰 16 pro", 1250000, "최신 스마트폰", 5));
        electronicProducts.add(new Product("갤럭시 버즈", 200000, "노이즈 캔슬링 이어폰", 20));

        Category electronic = new Category("전자제품", electronicProducts);

        // 2. 의류
        List<Product> clothingProducts = new ArrayList<>();
        clothingProducts.add(new Product("맨투맨", 45000, "오버핏 맨투맨", 100));

        Category clothing = new Category("의류", clothingProducts);


        // 3. 모든 카테고리를 하나의 리스트로 묶기
        List<Category> categories = new ArrayList<>();
        categories.add(electronic);
        categories.add(clothing);

        // 커머스 프로그램 생성
        CommerceSystem system = new CommerceSystem(categories);

        // 커머스 프로그램 시작
        system.start();
    }


}
