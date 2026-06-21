package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    // 캡슐화
    private final List<CartItem> items;

    // 생성자
    public Cart() {
        this.items = new ArrayList<>();
    }

    /* 장바구니가 비어있는지 확인 */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /* 장바구니 비우기 */
    public void clear(String message) {
        System.out.println(message);
        items.clear();
    }

    /* 상품 추가  */
    public void addProduct(Product product) {
        // 1. 동일 상품이 장바구니에 있는지 확인
        CartItem existItem = null;
        for(CartItem item : items){
            if(item.getProductName().equalsIgnoreCase(product.getName()) && item.getPrice() == product.getPrice()){
                existItem = item;
                break;
            }
        }

        // 2. 존재하면 수량 조절, 아니면 신규 추가
        if(existItem != null){
            if (product.getStockQuantity() < (existItem.getQuantity() + 1)) {
                System.out.println("❌ 실패: 매장 재고가 부족하여 장바구니에 담을 수 없습니다.");
                return;
            }
            existItem.addQuantity(1);
            System.out.println("❇️ 장바구니에 상품이 존재합니다. 수량이 1개 추가되었습니다. (현재: " + existItem.getQuantity() + "개)");
        } else {
            items.add(new CartItem(product.getName(), product.getPrice(), 1));
            System.out.println("✅ 장바구니에 " + product.getName() + "(1개)이 성공적으로 담겼습니다.");
        }
    }

    /* 장바구니 목록 및 총액 출력 (기존 CommerceSystem.printCart 로직) */
    public void print() {
        System.out.println("\n[ 🛒 내 장바구니 목록 ]");
        System.out.println("----------------------------------------------------------");

        if(items.isEmpty()){
            System.out.println("장바구니가 비어있습니다.");
            System.out.println("==========================================================");
            return;
        }

        int totalPrice = 0;
        for(CartItem item : items){
            System.out.println(item);
            totalPrice += (item.getPrice() * item.getQuantity());
        }
        System.out.println("----------------------------------------------------------");
        System.out.printf("%s %,12d원\n", "💵 총 결제 예정 금액 :", totalPrice);
        System.out.println("==========================================================");
    }

    /* 상품 삭제 */
    public boolean removeByProductName(String productName) {
        return items.removeIf(item -> item.getProductName().equalsIgnoreCase(productName));
    }


    public List<CartItem> getItems() {
        return this.items;
    }
}