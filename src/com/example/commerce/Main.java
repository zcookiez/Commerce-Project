package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 상품 리스트 생성
        List<Product> initProduct = new ArrayList<>();
        initProduct.add(new Product("맥북 에어", 1390000, "M3 가벼운 노트북", 10));
        initProduct.add(new Product("아이폰 16 pro", 1250000, "최신 스마트폰", 5));
        initProduct.add(new Product("갤럭시 버즈", 200000, "노이즈 캔슬링 이어폰", 20));

        CommerceSystem system = new CommerceSystem(initProduct);

        // 커머스 프로그램 시작
        system.start();
    }


}
