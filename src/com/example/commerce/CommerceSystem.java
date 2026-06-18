package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private List<Category> categories;

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
    }

    // 커머스 프로그램 시작
    public void start() {
        List<Product> products;
        Scanner sc = new Scanner(System.in);
        String str = String.format("%-15s | %-10s ", "0. 종료", "프로그램 종료");
        int no;

        do{
            // 전체 카테고리 출력
            System.out.println("[ 실시간 커머스 플랫폼 - 메인]");
            printCategories();

            // 종료 코드 출력
            System.out.println(str);

            // [사용자입력] 카테고리 번호 or 종료 코드 입력
            no = inputNum(sc);
            System.out.println( no + "번을 선택하셨습니다.");

            if(no == 0) {
                System.out.println("※  커머스 플랫폼을 종료합니다.");
            } else if (no > 0 && no <= categories.size()) {
                // 선택된 카테고리의 상품리스트 가져오기
                products = categories.get(no-1).getProducts();

                // 전체 상품 리스트 출력
                System.out.println("[ 실시간 커머스 플랫폼 -"+ categories.get(no-1).getCategory() +" ]");
                printProductList(products);

                // 종료 코드 출력
                System.out.println(str);

                // [사용자입력] 상품 번호 or 종료 코드 입력
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
            } else {
                // 없는 코드 입력
                System.out.println("❌ 존재하지 않는 카테고리 번호입니다. 다시 입력해주세요.");
            }

        }while(no != 0);
    }

    /*번호 입력*/
    public int inputNum(Scanner sc){
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

    /*카테고리 리스트 출력*/
    public void printCategories() {
        if(this.categories.isEmpty()){
            System.out.println("카테고리가 없습니다.");
        } else {
            int index = 1;
            for(Category category : this.categories){
                System.out.println( index++ + ". " + category.getCategory());
            }
        }
    }

    /*상품 리스트 출력*/
    public void printProductList(List<Product> products){
        if(products.isEmpty()){
            System.out.println("가지고 있는 상품이 없습니다.");
        }else {
            int index = 1;
            for(Product product : products){
                System.out.println( index++ + ". " + product.printList() );
            }
        }
    }
}
