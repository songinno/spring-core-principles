package hello.core.singleton;

public class StatefulService {
//    private int orderPrice; // 주문 가격 : 상태를 유지하는 필드

    public int order(String userName, int orderPrice) {
        System.out.println("userName = " + userName + ", orderPrice = " + orderPrice);
//        this.orderPrice = orderPrice; // ! 문제 발생 지점
        return orderPrice;
    }

//    public int getOrderPrice() {
//        return this.orderPrice;
//    }
}
