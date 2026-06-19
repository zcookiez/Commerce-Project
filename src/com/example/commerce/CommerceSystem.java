package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private final List<Category> categories;
    private final List<Cart> cart;
    private final Scanner sc; // 전역 스캐너
    private final String adminPwd = "admin"; // 관리자 패스워드

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        this.cart = new ArrayList<>(); // 처음 프로그램 시작할 때 장바구니는 빈상태로 생성!
        this.sc = new Scanner(System.in); // 👈 프로그램 켜질 때 딱 한 번만 생성
    }

    /*커머스 프로그램 시작*/
    public void start() {
        String exitStr = String.format("%-15s | %-10s ", "0. 종료", "프로그램 종료");
        String confirm = "장바구니 확인";
        String cancel = "주문 취소";
        String admin = "관리자 모드";

        Category selectedCategory;

        do {
            List<Category> menuList = new ArrayList<>(this.categories);

            // 카트가 비어있지 않을 때 장바구니 확인, 주문 취소 카테고리 추가
            if (!cart.isEmpty()) {
                menuList.add(new Category(confirm, new ArrayList<>()));
                menuList.add(new Category(cancel, new ArrayList<>()));
            }

            // 관리자 카테고리 추가
            menuList.add(new Category(admin, new ArrayList<>()));

            // 카테고리 메뉴 출력
            selectedCategory = selectItemFromMenu(
                    "[🌐 실시간 커머스 플랫폼 메인 ]",
                    menuList,
                    exitStr
            );

            // 커머스 카테고리 분개
            if (selectedCategory != null) {
                /*주문 확인*/
                if(selectedCategory.getCategory().equals(confirm)) order();
                /*주문 취소*/
                else if(selectedCategory.getCategory().equals(cancel)) clearCart("주문 취소로 장바구니를 모두 비웠습니다.");
                /*관리자 모드*/
                else if(selectedCategory.getCategory().equals(admin))  checkAdmin();
                /*상품 카테고리*/
                else runProductMenu(selectedCategory);
            }
        } while (selectedCategory != null); // 0번을 눌러 null이 반환되면 메인 루프도 종료
        System.out.println("※ 커머스 플랫폼을 종료합니다.");
    }

    /*카테고리의 상품 메뉴 출력*/
    private void runProductMenu(Category category) {
        Product selectedProduct;

        do {
            selectedProduct = selectItemFromMenu(
                    "[ 📃" + category.getCategory()+ " 카테고리 ]",
                    category.getProducts(),
                    "0. 뒤로가기"
            );
            // 사용자가 상품을 정확히 골랐다면 상세 정보 출력! > 장바구니 추가
            if (selectedProduct != null) {
                System.out.println(selectedProduct.printDetail());

                // 상품 장바구니에 담기
                int choice = inputNum("👉 번호를 입력해주세요 : ");

                if(choice == 1) addToCart(selectedProduct);
                else if(choice == 2) System.out.println("❌ 장바구니 추가를 취소하셨습니다.");
                else System.out.println("❌ 존재하지 않는 번호입니다. 올바른 번호(1 또는 2)를 선택해주세요.");
            }

        } while (selectedProduct != null); // 0번(뒤로가기)을 눌러 null이 반환되면 이 루프를 탈출해서 메인 메뉴로 복귀!
    }

    /*관리자 메뉴 출력*/
    private void runAdminMenu() {
        String exitStr = String.format("%-15s", "0. 메인으로 복귀");
        System.out.println("\n💕 관리자 인증에 성공했습니다.");

        // 제네릭 메서드에 넘겨줄 관리자용 메뉴 목록
        List<String> adminMenus = List.of(
                "상품 추가",
                "상품 수정",
                "상품 삭제",
                "전체 상품 현황"
        );

        String selectedMenu;
        do {
            selectedMenu = selectItemFromMenu(
                    "[ 🛠️ 관리자 전용 모드 ]",
                    adminMenus,
                    exitStr
            );

            // 반환된 문자열을 기반으로 메뉴 이동
            if (selectedMenu != null) {
                switch (selectedMenu) {
                    case "상품 추가" -> addProduct();
                    case "상품 수정" -> updateProduct();
                    case "상품 삭제" -> deleteProduct();
                    case "전체 상품 현황" -> showAllProducts();
                    default -> System.out.println("❌ 잘못된 메뉴 선택입니다. 다시 선택해 주세요.");
                }
            }
        } while (selectedMenu != null); // 0번을 누르면 null이 와서 깔끔하게 메인으로 탈출!
    }

    /*상품 장바구니에 담기*/
    private void addToCart(Product product){
        // 1. 상품의 재고 확인
        if (product.getStockQuantity() <= 0) {
            System.out.println("❌ 실패: 매장 재고가 부족하여 장바구니에 담을 수 없습니다.");
            return;
        }

        // 2. 동일 상품이 장바구니에 있는지 확인 (상품명과 가격 동일 여부 판단)
        Cart existItem = null;
        for(Cart item : cart){
            if(item.getProductName().equalsIgnoreCase(product.getName()) && item.getPrice() == product.getPrice()){
                existItem = item;
                break;
            }
        }

        // 3. 동일 상품이 존재하면 수량만 조절, 아니라면 장바구니 추가
        if(existItem != null){
            // 장바구니의 수량 + 1 과 상품의 재고를 비교 (주문 확정 전이라 상품의 재고가 차감되지 않은 상태)
            if (product.getStockQuantity() < (existItem.getQuantity() + 1)) {
                System.out.println("❌ 실패: 매장 재고가 부족하여 장바구니에 담을 수 없습니다.");
                return;
            }
            existItem.addQuantity(1);
            System.out.println("❇️ 장바구니에 상품이 존재합니다. 수량이 1개 추가되었습니다. (현재: " + existItem.getQuantity() + "개)");
        } else {
            cart.add(new Cart(product.getName(), product.getPrice(), 1));
            System.out.println("✅ 장바구니에 " + product.getName() + "(1개)이 성공적으로 담겼습니다.");
        }

        // 4. 장바구니 전체 목록 출력
        printCart();

    }

    /*장바구니 비우기*/
    private void clearCart(String str){
        System.out.println(str);
        cart.clear();
    }


    /*장바구니 목록 출력*/
    private void printCart(){
        System.out.println("\n[ 🛒 내 장바구니 목록 ]");
        System.out.println("----------------------------------------------------------");

        // 장바구니가 비어있는 상태인지 확인
        if(cart.isEmpty()){
            System.out.println("장바구니가 비어있습니다.");
            System.out.println("==========================================================");
            return;
        }

        int totalPrice = 0;
        for(Cart item : cart){
            System.out.println(item);
            totalPrice += (item.getPrice() * item.getQuantity());
        }
        System.out.println("----------------------------------------------------------");
        System.out.printf("%s %,12d원\n", "💵 총 결제 예정 금액 :", totalPrice);
        System.out.println("==========================================================");
    }

    /* 장바구니 주문 확정*/
    private void order(){
        System.out.println("\n\n😊아래와 같이 주문 하시겠습니까?");
        printCart();
        System.out.println("1. 주문 확정        2. 메인으로 돌아가기\n");
        int choice = inputNum("👉 번호를 입력해주세요 : ");

        if(choice == 1){
            // 주문 확정 전 최종 재고 확인
            if(!checkFinalStock()){
                System.out.println("❌ 실패: 매장 재고가 부족하여 주문할 수 없습니다.");
                return;
            }
            deductStock();
            clearCart("주문이 완료되었습니다.");
        }
        else if(choice == 2){
            System.out.println("주문이 취소되었습니다.");
            return;
        }
        else System.out.println("❌ 존재하지 않는 번호입니다. 올바른 번호(1 또는 2)를 선택해주세요.");
    }

    /*상품 재고 확인*/
    private boolean checkFinalStock() {
        for (Cart cartItem : cart) {
            for (Category category : this.categories) {
                for (Product product : category.getProducts()) {
                    if (product.getName().equalsIgnoreCase(cartItem.getProductName())) {
                        // 실제 매장 재고가 내가 주문하려는 수량보다 적다면 즉시 실패(false) 처리!
                        if (product.getStockQuantity() < cartItem.getQuantity()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true; // 모든 상품의 재고가 안전하게 남아있다면 true 반환!
    }

    /* 재고 차감*/
    private void deductStock() {
        System.out.println("\n📦 매장 상품 재고 차감을 진행합니다.");

        for (Cart cartItem : cart) {
            for (Category category : this.categories) {
                for (Product product : category.getProducts()) {
                    // 장바구니에 담긴 상품명과 실제 매대 상품명이 일치하는 경우
                    if (product.getName().equalsIgnoreCase(cartItem.getProductName())) {
                        int currentStock = product.getStockQuantity();
                        int orderQuantity = cartItem.getQuantity();

                        // 실제 재고 차감 반영
                        product.setStockQuantity(currentStock - orderQuantity);

                        System.out.printf("  %s (기존 재고: %d개 -> 남은 재고: %d개)\n",
                                product.getName(), currentStock, product.getStockQuantity());
                    }
                }
            }
        }
    }

    /*관리자 체크*/
    private void checkAdmin(){
        int failCnt = 0;
        final int MAX_ATTEMPTS = 3;

        System.out.println("\n[🔒 관리자 인증]");

        // 3번까지 로그인 체크
        while (failCnt < MAX_ATTEMPTS) {
            String inputPw = inputStr("🗝️ 관리자 비밀번호를 입력해주세요 : ");

            if (inputPw.equals(adminPwd)) {
                runAdminMenu();
                return;
            } else {
                failCnt++;
                System.out.printf("❌ 비밀번호가 일치하지 않습니다. (남은 횟수: %d회)\n", MAX_ATTEMPTS - failCnt);
            }
        }
        System.out.println("\n🚨 비밀번호 3회 입력 실패!");
    }


    /*상품 추가*/
    private void addProduct() {
        //1. 카테고리 메뉴 출력
        Category selectedCategory = selectItemFromMenu(
                "[➕ 추가할 상품의 카테고리 ]",
                this.categories,
                "어느 카테고리에 추가하시겠습니까?"
        );

        System.out.println("===========================================");

        // 2. 콘솔로부터 상품 상세 정보 입력받기
        String name = inputStr("🔖 상품명을 입력해주세요: ");
        int price = inputNum("💵 가격을 입력해주세요: ");
        String description = inputStr("📝 상품 설명을 입력해주세요: ");
        int stock = inputNum("📦 재고수량을 입력해주세요: ");

        System.out.println("===========================================");
        // 3 Product 객체 생성 후 해당 카테고리의 상품 리스트에 주입
        selectedCategory.getProducts().add(new Product(name, price,description, stock));

        System.out.printf("\n✅ [%s] 카테고리에 '%s' 상품이 성공적으로 등록되었습니다!\n",
                selectedCategory.getCategory(), name);
    }

    /*상품 수정*/
    private void updateProduct() {
        //1. 카테고리 메뉴 출력
        Category selectedCategory = selectItemFromMenu(
                "[🪄 수정할 상품의 카테고리 ]",
                this.categories,
                "어느 카테고리의 상품을 수정하시겠습니까?"
        );

        System.out.println("===========================================");

        // 2. 상품 메뉴 출력
        Product selectedProduct = selectItemFromMenu(
                "[ ✒️" + selectedCategory.getCategory()+ " 카테고리 ]",
                selectedCategory.getProducts(),
                "어떤 상품을 수정하시겠습니까?"
        );

        System.out.println("===========================================");


        // 3. 선택된 상품의 수정할 항목 선택
        List<String> updateMenus = List.of(
                "가격",
                "설명",
                "재고수량"
        );

        String selectedField = selectItemFromMenu(
                    "[ 🛠️ 상품 정보 수정 ]",
                    updateMenus,
                    "0. 뒤로가기"
        );

        // 4. 항목 수정
        if (selectedField != null) {
            switch (selectedField) {
                case "가격" -> {
                    int newPrice = inputNum("💵 새로운 가격을 입력해주세요: ");
                    selectedProduct.setPrice(newPrice);
                    System.out.println("✅ 가격이 " + newPrice + "원으로 수정되었습니다.");
                }
                case "설명" -> {
                    String newDesc = inputStr("📝 새로운 설명을 입력해주세요: ");
                    selectedProduct.setDescription(newDesc);
                    System.out.println("✅ 설명이 수정되었습니다.");
                }
                case "재고수량" -> {
                    int newStock = inputNum("📦 새로운 재고수량을 입력해주세요: ");
                    selectedProduct.setStockQuantity(newStock);
                    System.out.println("✅ 재고수량이 " + newStock + "개로 수정되었습니다.");

                }
            }
        }

    }
    private void deleteProduct() { System.out.println("🗑️ 상품 삭제 기능 구현 예정"); }
    private void showAllProducts() { System.out.println("📊 전체 상품 현황 출력 예정"); }

    /*CommerceSystem 제네릭 메서드 (category, product 두 케이스 모두 받기 위해 필요)*/
    private <T> T selectItemFromMenu(String title, List<T> list, String text) {
        while (true) {
            System.out.println("\n" + title);

            // 1. 목록  출력
            int index = 1;
            for (T item : list) {
                System.out.println(index++ + ". " + item.toString());
            }
            System.out.println(text);

            // 2. 사용자 입력 받기
            int no = inputNum("👉 번호를 입력해주세요 : ");
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
    private int inputNum(String text){
        while (true) { // 올바른 숫자를 입력할 때까지 무한 반복
            try {
                System.out.print(text);
                int result = this.sc.nextInt();
                this.sc.nextLine(); // Scanner 버퍼 비우기
                return result;
            } catch (java.util.InputMismatchException e) {
                System.out.println("⚠️ 숫자만 입력할 수 있습니다. 다시 시도해주세요.");
                this.sc.next(); // Scanner 버퍼 비우기
            }
        }
    }

    /*문자열 입력*/
    private String inputStr(String text){
        System.out.print(text);
        return this.sc.nextLine();
    }

}
