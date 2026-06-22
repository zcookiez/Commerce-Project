# 🌐 실시간 콘솔 쇼핑몰 프로그램 (CommerceSystem)

이 프로그램은 Java 언어로 작성된 **콘솔 화면 기반의 간단한 커머스 프로젝트**입니다. 

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

프로그램을 구성하는 7개 클래스의 핵심 역할, 주요 속성, 메서드 및 관계를 보기 좋게 요약한 명세 리스트

---

### 🚀 [Main](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Main.java) (프로그램 시작점)
- **담당 역할**: 초기 카테고리와 상품 리스트를 등록하고 프로그램을 최초 가동합니다.
- **주요 속성**: 없음
- **핵심 메서드**: `main()` (기본 데이터 구성 및 쇼핑몰 구동 시작)
- **연관 관계**: [CommerceSystem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CommerceSystem.java)을 생성 및 실행합니다.

### ⚙️ [CommerceSystem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CommerceSystem.java) (화면 제어 및 전체 조율)
- **담당 역할**: 쇼핑몰 메인 콘솔의 입출력 화면을 제어하고 전체 흐름 및 관리자 모드를 관리합니다.
- **주요 속성**: `categories` (카테고리 목록), `cart` (사용자 장바구니), `sc` (콘솔 입력용 스캐너)
- **핵심 메서드**: 
  - `start()`, `runProductMenu()`, `runAdminMenu()` (화면 및 메뉴 이동 제어)
  - `order()`, `checkFinalStock()`, `deductStock()` (주문 확정 및 재고 차감 처리)
  - `addProduct()`, `updateProduct()`, `deleteProduct()`, `showAllProducts()` (관리자용 상품 변경 기능)
  - `checkAdmin()`, `findProductByName()`, `inputNum()`, `inputStr()` (인증 및 안전 입력 제어 유틸)
- **연관 관계**: 장바구니([Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java))와 카테고리 목록([Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java))을 소유하고 조율합니다.

### 🏷️ [Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java) (상품 분류 박스)
- **담당 역할**: 카테고리 정보와 그 하위에 소속된 상품들의 리스트를 묶어서 관리합니다.
- **주요 속성**: `category` (카테고리명), `products` (분류 내 상품 리스트)
- **핵심 메서드**: `addProduct()`, `removeProduct()` (상품 신규 등록 및 제외 삭제)
- **연관 관계**: 다수의 상품([Product](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Product.java))들을 목록 형태로 소유합니다.

### 📦 [Product](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Product.java) (개별 상품 정보 보관)
- **담당 역할**: 개별 상품의 세부 정보(이름, 가격, 설명, 재고)를 보관하고, 잘못된 값이 입력되지 않도록 방어합니다.
- **주요 속성**: `name` (상품명), `price` (가격), `description` (설명), `stockQuantity` (매장 내 실재고 수량)
- **핵심 메서드**: 
  - `setPrice()`, `setStockQuantity()` (금액 및 재고 변경 시 음수를 0으로 강제하는 검증 처리)
  - `printDetail()`, `toString()` (상품 스펙을 줄 맞춰 포맷팅 출력)
- **연관 관계**: 카테고리([Category](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Category.java))의 관리 품목 리스트에 포함되어 소속됩니다.

### 🛒 [Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java) (장바구니 계산기)
- **담당 역할**: 사용자가 담은 품목 목록을 캡슐화(숨겨둠)하여 보관하며, 중복 추가 검사 및 합산 결제액을 계산합니다.
- **주요 속성**: `items` (장바구니에 담긴 세부 품목 리스트)
- **핵심 메서드**: 
  - `addProduct()` (담으려는 상품의 중복 유무를 판별해 새로 추가하거나 수량을 가산함)
  - `print()` (현재 담긴 품목들과 총 청구 결제 금액을 계산하여 서식화 출력)
  - `clear()` (주문 완료 및 취소에 따른 카트 청소), `removeByProductName()` (상품 삭제 시 카트에서도 자동 삭제)
- **연관 관계**: 다수의 장바구니 개별 아이템([CartItem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CartItem.java))들을 소유하여 제어합니다.

### 📝 [CartItem](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/CartItem.java) (장바구니 개별 품목)
- **담당 역할**: 장바구니에 담긴 특정 상품의 명칭, 담긴 당시의 단가, 사용자가 지정한 수량만을 추려서 기억합니다.
- **주요 속성**: `productName` (상품명), `price` (단가), `quantity` (담은 개수)
- **핵심 메서드**: `addQuantity()` (장바구니 안에서 수량이 늘어날 시 누적 가산), `toString()` (품목별 중간 가격 계산 출력)
- **연관 관계**: 장바구니([Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java)) 내부 리스트에 적재되어 존재합니다.

### 👤 [Customer](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Customer.java) (이용자 정보 보관)
- **담당 역할**: 이용 손님의 신원 정보(이름, 이메일, 회원등급)를 보관하는 정보 바구니입니다.
- **주요 속성**: `customerName` (고객명), `email` (메일 주소), `rank` (등급)
- **핵심 메서드**: 각 필드에 대한 기본적인 Getter 및 Setter 메서드
- **연관 관계**: 향후 회원 맞춤 혜택이나 구매 기록 연계를 위해 장바구니([Cart](file:///C:/Practice_3/CommerceProject/src/com/example/commerce/Cart.java))와 연결될 개념적 확장 포인트입니다.

---
