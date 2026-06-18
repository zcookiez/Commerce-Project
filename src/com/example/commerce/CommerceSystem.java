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
        Scanner sc = new Scanner(System.in);
        String exitStr = String.format("%-15s | %-10s ", "0. 종료", "프로그램 종료");
        Category category;
        do {
            // 카테고리 메뉴 출력
            category = selectItemFromMenu(
                    sc,
                    "[ 실시간 커머스 플랫폼 메인 ]",
                    this.categories,
                    exitStr
            );
            // 사용자가 0번을 누르지 않았고, 올바른 카테고리를 골랐다면 상품 리스트 출력
            if (category != null) {
                runProductMenu(sc, category);
            }
        } while (category != null); // 0번을 눌러 null이 반환되면 메인 루프도 종료
        System.out.println("※ 커머스 플랫폼을 종료합니다.");
    }

    // 카테고리의 상품 메뉴 출력
    private void runProductMenu(Scanner sc, Category category) {
        Product product;
        String exitStr = String.format("%-15s", "0. 뒤로가기");
        do {
            product = selectItemFromMenu(
                    sc,
                    "[ " + category.toString()+ " 카테고리 ]",
                    category.getProducts(),
                    exitStr
            );
            // 사용자가 상품을 정확히 골랐다면 상세 정보 출력!
            if (product != null) {
                System.out.println(product.printDetail());
            }

        } while (product != null); // 0번(뒤로가기)을 눌러 null이 반환되면 이 루프를 탈출해서 메인 메뉴로 복귀!
    }

    // CommerceSystem 제네릭 메서드 (category, product 두 케이스 모두 받기 위해 필요)
    private <T> T selectItemFromMenu(Scanner sc, String title, List<T> list, String exitText) {
        while (true) {
            System.out.println("\n" + title);

            // 1. 목록  출력
            int index = 1;
            for (T item : list) {
                System.out.println(index++ + ". " + item.toString());
            }
            System.out.println(exitText);

            // 2. 사용자 입력 받기
            int no = inputNum(sc);
            System.out.println(no + "번을 선택하셨습니다.");

            // 3. 검증 및 반환
            if (no == 0) {
                return null; // 0번을 누르면 상위 메뉴로 돌아가거나 종료하도록 null 반환
            } else if (no > 0 && no <= list.size()) {
                return list.get(no - 1); // 올바른 번호면 선택된 객체 반환
            } else {
                System.out.println("❌ 존재하지 않는 번호입니다. 다시 입력해주세요.");
            }
        }
    }

    /*번호 입력*/
    public int inputNum(Scanner sc){
        while (true) { // 올바른 숫자를 입력할 때까지 무한 반복
            try {
                System.out.print("번호를 입력해주세요 : ");
                return sc.nextInt(); // 정상적인 숫자면 그대로 반환 후 종료

            } catch (java.util.InputMismatchException e) {
                System.out.println("⚠️ 숫자만 입력할 수 있습니다. 다시 시도해주세요.");
                // Scanner 버퍼 비우기
                sc.next();
            }
        }
    }


}
