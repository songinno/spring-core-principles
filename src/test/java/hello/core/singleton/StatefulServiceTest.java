package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void order() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
        // ThreadA : A 사용자 - 10000원 주문
//        statefulService1.order("userA", 10000);
        int orderPriceA = statefulService1.order("userA", 10000);
        // ThreadB : B 사용자 - 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA : A 사용자 - 주문 금액 조회
//        int orderPriceA = statefulService1.getOrderPrice();
        // 10000원이 출력되길 기대했으나, 20000이 출력됨 -> 10000원 출력
        System.out.println("orderPriceA = " + orderPriceA); // orderPriceA = 20000 -> orderPriceA = 10000


    }

    static class TestConfig {
        @Bean
        static StatefulService statefulService() {
            return new StatefulService();
        }
    }

}