package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private final List<Category> categories;
    private final Cart cart;
    private final Scanner sc; // 전역 스캐너

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        this.cart = new Cart(); // 👈 프로그램 시작 시 빈 장바구니 객체 하나를 생성하여 쥐어줌
        this.sc = new Scanner(System.in);
    }

    /*커머스 프로그램 시작*/
    public void start() {
        String confirm = "장바구니 확인";
        String cancel = "주문 취소";
        String admin = "관리자 모드";

        Printable selectedCategory;

        do {
            List<Printable> menuList = new ArrayList<>(this.categories);

            // 카트가 비어있지 않을 때 장바구니 확인, 주문 취소 카테고리 추가
            if (!cart.isEmpty()) {
                menuList.add(new MenuOption(confirm));
                menuList.add(new MenuOption(cancel));
            }
            // 관리자 카테고리 추가
            menuList.add(new MenuOption(admin));

            // 카테고리 메뉴 출력
            selectedCategory = selectItemFromMenu(
                    "[🌐 실시간 커머스 플랫폼 메인 ]",
                    menuList,
                    "0. 프로그램 종료"
            );

            // 커머스 카테고리
            if (selectedCategory != null) {
                if (selectedCategory instanceof Category) {
                    runProductMenu((Category) selectedCategory);
                } else if (selectedCategory instanceof MenuOption) {
                    String menuName = ((MenuOption) selectedCategory).getMenuName();
                    if (menuName.equals(confirm)) order();
                    if (menuName.equals(cancel)) cart.clear("주문 취소로 장바구니를 모두 비웠습니다.");
                    if (menuName.equals(admin))  checkAdmin();
                }

            }
        } while (selectedCategory != null);
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
            if (selectedProduct != null) {
                System.out.println(selectedProduct.printDetail());
                System.out.println("🛒 위 상품을 장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인        2. 취소\n");

                int choice = inputNum("👉 번호를 입력해주세요 : ");

                if(choice == 1) addToCart(selectedProduct);
                else if(choice == 2) System.out.println("❌ 장바구니 추가를 취소하셨습니다.");
                else System.out.println("❌ 존재하지 않는 번호입니다. 올바른 번호(1 또는 2)를 선택해주세요.");
            }
        } while (selectedProduct != null);
    }

    /*관리자 메뉴 출력*/
    private void runAdminMenu() {
        System.out.println("\n💕 관리자 인증에 성공했습니다.");
        String add = "상품 추가";
        String update = "상품 수정";
        String delete = "상품 삭제";
        String showAll = "전체 상품 현황";

        List<MenuOption> adminMenus = List.of(
                new MenuOption(add),
                new MenuOption(update),
                new MenuOption(delete),
                new MenuOption(showAll)
        );

        MenuOption selectedMenu;
        do {
            selectedMenu = selectItemFromMenu(
                    "[ 🛠️ 관리자 전용 모드 ]",
                    adminMenus,
                    "0. 뒤로가기"
            );

            if (selectedMenu != null) {
                String menuName = selectedMenu.getMenuName();

                if(menuName.equals(add)) addProduct();
                else if(menuName.equals(update)) updateProduct();
                else if(menuName.equals(delete))  deleteProduct();
                else if(menuName.equals(showAll))  showAllProducts();
                else System.out.println("❌ 잘못된 메뉴 선택입니다. 다시 선택해 주세요.");
            }
        } while (selectedMenu != null);
    }

    /*상품 장바구니에 담기*/
    private void addToCart(Product product){
        // 재고 검증 로직은 유지
        if (product.getStockQuantity() <= 0) {
            System.out.println("❌ 실패: 매장 재고가 부족하여 장바구니에 담을 수 없습니다.");
            return;
        }

        cart.addProduct(product);
        System.out.println(cart.getReceipt());
    }

    /* 장바구니 주문 확정*/
    private void order(){
        System.out.println("\n\n😊아래와 같이 주문 하시겠습니까?");
        System.out.println(cart.getReceipt());
        System.out.println("1. 주문 확정        2. 메인으로 돌아가기\n");
        int choice = inputNum("👉 번호를 입력해주세요 : ");

        if(choice == 1){
            if(!checkFinalStock()){
                System.out.println("❌ 실패: 매장 재고가 부족하여 주문할 수 없습니다.");
                return;
            }
            deductStock();
            cart.clear("주문이 완료되었습니다."); // 💡 cart에게 비우기 명령
        }
        else if(choice == 2){
            System.out.println("주문이 취소되었습니다.");
        }
        else System.out.println("❌ 존재하지 않는 번호입니다. 올바른 번호(1 또는 2)를 선택해주세요.");
    }

    /*상품 추가*/
    private void addProduct() {
        System.out.println("\n어느 카테고리에 상품을 추가하시겠습니까?");
        Category selectedCategory = selectItemFromMenu("[➕ 추가할 상품의 카테고리 ]", this.categories, "0. 뒤로가기");
        if (selectedCategory == null) return;

        System.out.println("===========================================");
        String name = inputStr("🔖 상품명을 입력해주세요: ");

        Product existProduct = findProductByName(name);
        if(existProduct != null) {
            System.out.println("\n🔥 동일한 상품명이 존재합니다. 상품 추가가 아닌 수정으로 진행해주세요.");
            return;
        }

        int price = inputNum("💵 가격을 입력해주세요: ");
        String description = inputStr("📝 상품 설명을 입력해주세요: ");
        int stock = inputNum("📦 재고수량을 입력해주세요: ");

        System.out.println("===========================================");
        selectedCategory.addProduct(new Product(name, price, description, stock)); // Category 객체에게 위임
        System.out.printf("\n✅ [%s] 카테고리에 '%s' 상품이 성공적으로 등록되었습니다!\n", selectedCategory.getCategory(), name);
    }

    /*상품 수정*/
    private void updateProduct() {
        //1. 카테고리 메뉴 출력
        Category selectedCategory = selectItemFromMenu(
                "[🪄 수정할 상품의 카테고리 ]"
                , this.categories
                , "0. 뒤로가기"
        );
        if (selectedCategory == null) return;

        System.out.println("===========================================");

        // 2. 상품 메뉴 출력
        Product selectedProduct = selectItemFromMenu(
                "[ ✒️" + selectedCategory.getCategory()+ " 카테고리 ]"
                , selectedCategory.getProducts()
                , "0. 뒤로가기"
        );
        if (selectedProduct == null) return;

        System.out.println("===========================================");


        // 3. 선택된 상품의 수정할 항목 선택
        List<MenuOption> updateMenus = List.of(
                new MenuOption("가격"),
                new MenuOption("설명"),
                new MenuOption("재고수량")
        );

        MenuOption selectedField = selectItemFromMenu(
                "[ 🛠️ 상품 정보 수정 ]"
                , updateMenus
                , "0. 뒤로가기"
        );
        if (selectedField == null) return;

        String fieldName = selectedField.getMenuName();

        // 4. 항목 수정
        switch (fieldName) {
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

    /*상품 삭제*/
    private void deleteProduct() {
        System.out.println("\n어느 카테고리의 상품을 삭제하시겠습니까?");
        Category selectedCategory = selectItemFromMenu(
                "[🗑 삭제할 상품의 카테고리 ]"
                , this.categories
                , "0. 뒤로가기"
        );
        if (selectedCategory == null) return;

        System.out.println("===========================================");
        System.out.println("\n어떤 상품을 삭제하시겠습니까?");
        Product selectedProduct = selectItemFromMenu(
                "[ 🗑️" + selectedCategory.getCategory()+ " 카테고리 ]"
                , selectedCategory.getProducts()
                , "0. 뒤로가기"
        );
        if (selectedProduct == null) return;

        System.out.println("===========================================");
        System.out.printf("\n🗑 정말로 '%s' 상품을 삭제하시겠습니까?", selectedProduct.getName());
        System.out.println(selectedProduct.printDetail());
        System.out.println("1. 예, 삭제합니다.     2. 아니오, 취소합니다.");
        int confirm = inputNum("👉 번호를 입력해주세요: ");

        if (confirm == 1) {
            selectedCategory.removeProduct(selectedProduct); // 💡 Category 객체에게 위임
            System.out.printf("✅ [%s] 카테고리에서 '%s' 상품이 완전히 삭제되었습니다.\n", selectedCategory.getCategory(), selectedProduct.getName());

            // 장바구니 삭제
            boolean isRemovedFromCart = cart.removeByProductName(selectedProduct.getName());
            if (isRemovedFromCart) {
                System.out.println("🚨 고객 장바구니에 담겨있던 해당 상품도 함께 삭제 처리되었습니다.");
            }
        } else {
            System.out.println("❌ 상품 삭제가 취소되었습니다.");
        }
    }

    /*전체 상품 출력*/
    private void showAllProducts() {
        System.out.println("\n📊 전체 상품 현황을 출력합니다.");

        this.categories.forEach(category -> {
            System.out.println("\n📌 " + category.getCategory());
            System.out.println("--------------------------------------------");
            category.getProducts().stream()
                    .map(Product::printFormat)
                    .forEach(System.out::println);
            System.out.println("=============================================");
        });
    }
    /*CommerceSystem 제네릭 메서드*/
    private <T extends Printable> T selectItemFromMenu(String title, List<T> list, String text) {
        while (true) {
            System.out.println("\n" + title);

            // 1. 목록  출력
            int index = 1;
            for (T item : list) {
                System.out.println(index++ + ". " + item.printFormat());
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

    /*상품 재고 확인*/
    private boolean checkFinalStock() {
        // noneMatch: 아래 조건(재고가 부족함)에 일치하는 항목이 "단 하나도 없어야" true를 반환
        return cart.getItems().stream()
                .noneMatch(cartItem -> {
                    Product product = findProductByName(cartItem.getProductName());
                    return product != null && product.getStockQuantity() < cartItem.getQuantity();
                });
    }

    /*상품 재고 차감*/
    private void deductStock() {
        System.out.println("\n📦 매장 상품 재고 차감을 진행합니다.");

        // 💡 장바구니 내부의 알맹이(CartItem)들을 꺼내서 차감
        for (CartItem cartItem : cart.getItems()) {
            Product product = findProductByName(cartItem.getProductName());
            if (product != null) {
                int currentStock = product.getStockQuantity();
                int orderQuantity = cartItem.getQuantity();

                product.setStockQuantity(currentStock - orderQuantity);
                System.out.printf("  %s (기존 재고: %d개 -> 남은 재고: %d개)\n", product.getName(), currentStock, product.getStockQuantity());
            }
        }
    }

    /*관리자 체크*/
    private void checkAdmin(){
        int failCnt = 0;
        final int MAX_ATTEMPTS = 3;
        System.out.println("\n[🔒 관리자 인증]");

        while (failCnt < MAX_ATTEMPTS) {
            String inputPw = inputStr("🗝️ 관리자 비밀번호를 입력해주세요 : ");
            String adminPwd = "admin";
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

    /*상품명으로 상품 객체 찾기*/
    private Product findProductByName(String productName) {
        return this.categories.stream()
                .flatMap(category -> category.getProducts().stream())
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);
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