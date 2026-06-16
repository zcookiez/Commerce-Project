package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 선언 및 초기화
        Scanner sc = new Scanner(System.in);
        Product product = new Product();
        List<Product> products = new ArrayList<>();
        String str = String.format("%-15s | %-10s ", "0. 종료", "프로그램 종료");
        int no;

        // 상품 추가
        products.add(new Product("맥북 에어", 1390000, "M3 가벼운 노트북", 10));
        products.add(new Product("아이폰 16 pro", 1250000, "최신 스마트폰", 5));
        products.add(new Product("갤럭시 버즈", 200000, "노이즈 캔슬링 이어폰", 20));

        do{
            // 전체 상품 리스트 출력
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
            printProductList(products);

            // 종료 코드 출력
            System.out.println(str);

            // 사용자 상품 번호 or 종료 코드 입력
            no = inputNum(sc);
            System.out.println( no + "번을 선택하셨습니다.");

            if (no == 0) {  // 종료 코드 입력
                System.out.println("※  커머스 플랫폼을 종료합니다.");
            } else if (no > 0 && no <= products.size()) { // 상품 상세 출력
                Product selectedProduct = products.get(no - 1);
                System.out.println(selectedProduct.printDetail());
            } else { // 없는 코드 입력
                System.out.println("❌ 존재하지 않는 상품 번호입니다. 다시 입력해주세요.");
            }

        }while(no != 0);
    }

    /*번호 입력*/
    public static int inputNum(Scanner sc){
        while (true) { // 올바른 숫자를 입력할 때까지 무한 반복
            try {
                System.out.print("번호를 입력해주세요 : ");
                return sc.nextInt(); // 정상적인 숫자면 그대로 반환 후 종료

            } catch (java.util.InputMismatchException e) {
                System.out.println("⚠️ 숫자만 입력할 수 있습니다. 다시 시도해주세요.");
                // ⚠️ [매우 중요] Scanner 버퍼 비우기
                sc.next();
            }
        }
    }

    /*상품 리스트 출력*/
    public static void printProductList(List<Product> products){
        if(products.isEmpty()){
            System.out.println("가지고 있는 상품이 없습니다.");
        }else {
            int index = 1;
            for(Product product : products){
                System.out.println( index++ + ". " +product.printList() );
            }
        }
    }
}
