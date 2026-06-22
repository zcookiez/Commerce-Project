# 🌐 실시간 콘솔 쇼핑몰 프로그램 (CommerceSystem)

이 프로그램은 Java 언어로 작성된 **콘솔 화면 기반의 간단한 쇼핑몰 토이 프로젝트**입니다. 

---

# 1. 프로젝트 개요

### 🛠️ 구현 기능
- **고객 기능**: 카테고리별 상품 둘러보기, 상품 상세 정보 확인, 장바구니 담기(매장 재고가 있을 때만 가능), 장바구니 목록 및 최종 합산 금액 확인, 주문 확정 및 재고 차감.
- **관리자 기능**: 비밀번호(`admin`) 인증 로그인, 새로운 상품 추가, 기존 상품의 정보(가격, 설명, 재고수량) 수정, 상품 삭제(장바구니에 담겨 있던 상품도 함께 자동 삭제), 매장의 전체 상품 목록 한눈에 보기.

### 🏛️ 주요 객체 설명
1. **[CommerceSystem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CommerceSystem.java)**: 전체 쇼핑몰 프로그램의 메인 화면을 띄우고 메뉴 이동을 안내해주는 지휘자 역할을 합니다.
2. **[Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java)**: 고객이 고른 물건들을 보관하고, 수량을 더하거나 빼주는 장바구니 상자입니다.
3. **[Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java) & [Product](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Product.java)**: 상품들을 분류(예: 전자제품, 의류)하고, 가격이나 재고가 음수(0보다 작은 수)가 되지 않도록 제어하는 역할을 합니다.

---

# 2. 클래스 구조 및 역할

프로그램을 구성하는 7개 클래스의 핵심 역할, 보관하는 속성, 주요 기능 및 관계를 한눈에 볼 수 있도록 요약한 표입니다.

| 클래스명 | 담당 역할 (책임) | 주요 속성 | 핵심 메서드 | 연관 관계 |
| :--- | :--- | :--- | :--- | :--- |
| 🚀 [Main](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Main.java) | **프로그램 시작점**<br>초기 데이터 설정 및 시스템 구동 | 없음 | - `main()`: 초기 카테고리/상품을 세팅하고 쇼핑몰 가동 | - [CommerceSystem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CommerceSystem.java)을 생성 및 실행 |
| ⚙️ [CommerceSystem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CommerceSystem.java) | **화면 제어 및 전체 조율**<br>메뉴 이동, 사용자 입력 및 관리자 모드 처리 | - `categories`: 판매 카테고리 목록<br>- `cart`: 장바구니 객체<br>- `sc`: 콘솔 입력용 스캐너 | - `start()`, `runProductMenu()`, `runAdminMenu()` (메뉴 구동)<br>- `order()`, `checkFinalStock()`, `deductStock()` (주문 & 재고)<br>- `addProduct()`, `updateProduct()`, `deleteProduct()`, `showAllProducts()` (상품 관리)<br>- `checkAdmin()`, `findProductByName()`, `inputNum()`, `inputStr()` (인증 및 유틸) | - [Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java) 및 [Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java)를 직접 관리 |
| 🏷️ [Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java) | **상품 분류 박스**<br>카테고리명 관리 및 소속 상품 목록 제어 | - `category`: 분류 이름 (예: 전자제품)<br>- `products`: 소속 상품 리스트 | - `addProduct()` / `removeProduct()`: 상품 등록 및 삭제 | - 다수의 [Product](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Product.java)를 리스트로 소유 |
| 📦 [Product](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Product.java) | **개별 상품 정보 보관**<br>이름, 가격, 재고 관리 및 값 검증 | - `name`: 상품명<br>- `price` / `stockQuantity`: 가격 및 재고 수량<br>- `description`: 상세 설명 | - `setPrice()`, `setStockQuantity()` (음수 입력 차단)<br>- `printDetail()`, `toString()` (상세 및 메뉴형 서식화 출력) | - [Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java) 리스트에 포함됨 |
| 🛒 [Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java) | **장바구니 계산기**<br>담긴 품목 관리 및 금액 계산 | - `items`: 담긴 품목 목록 (`CartItem`) | - `addProduct()`: 담기 시 재고 검증 및 수량 누적<br>- `print()`: 목록 및 총 주문액 계산 출력<br>- `clear()`: 청소<br>- `removeByProductName()`: 상품명으로 안전 제거 | - 다수의 [CartItem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CartItem.java)을 소유 |
| 📝 [CartItem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CartItem.java) | **장바구니 개별 품목**<br>선택된 상품의 수량 상태 기억 | - `productName`: 상품명<br>- `price` / `quantity`: 단가 및 담긴 수량 | - `addQuantity()`: 수량 누적 가산 | - [Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java) 내부에 담겨 관리됨 |
| 👤 [Customer](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Customer.java) | **이용자 정보 보관**<br>고객 이름, 이메일, 등급 홀더 | - `customerName` / `email` / `rank` | - Getter / Setter 메서드 | - 향후 [Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java)와 연결될 확장 포인트 |

---
