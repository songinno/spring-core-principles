package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SingletonIndiTest {


    @Test
    @DisplayName("@Configuration을 안붙였을 때, 싱글톤 보장이 안되는 예시")
    void noSingletonTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        Person person = ac.getBean(Person.class);
        Animal animal = ac.getBean(Animal.class);

        Hand hand1 = person.getHand();
        Hand hand2 = animal.getHand();
        System.out.println("hand1 = " + hand1);
        System.out.println("hand2 = " + hand2);
        /* @Configuration 안붙인 경우 */
        // hand1 = hello.core.singleton.SingletonIndiTest$Hand@17a1e4ca
        // hand2 = hello.core.singleton.SingletonIndiTest$Hand@10ded6a9

        /* @Configuration 붙인 경우 */
        // hand1 = hello.core.singleton.SingletonIndiTest$Hand@63b1d4fa
        // hand2 = hello.core.singleton.SingletonIndiTest$Hand@63b1d4fa

        Person person1 = ac.getBean(Person.class);
        Person person2 = ac.getBean(Person.class);
        Hand hand3 = person1.getHand();
        Hand hand4 = person2.getHand();
        System.out.println("hand3 = " + hand3);
        // hand3 = hello.core.singleton.SingletonIndiTest$Hand@17a1e4ca
        System.out.println("hand4 = " + hand4);
        // hand4 = hello.core.singleton.SingletonIndiTest$Hand@17a1e4ca

        /* 정리 - @Configuration을 안붙였을 경우
        *
        * 서로 다른 참조 타입의 Bean을 처음 만들 때만 Hand에 대한 싱글톤으로 보장되지 않음(Person과 Animal를 각각 만들 때)
        * @Bean은 기본적으로 싱글톤 스코프를 가지므로,
        *   이미 Bean으로 생성되고 나면, 그 이후 같은 Bean 객체를 호출(조회)할 시에는, 동일한 주소 값을 반환(person1, person2)
        * */


    }

    static class Person {
        private final Hand hand;

        Person(Hand hand) {
            this.hand = hand;
        }

        public Hand getHand() {
            return hand;
        }
    }

    static class Animal {
        private final Hand hand;

        Animal(Hand hand) {
            this.hand = hand;
        }

        public Hand getHand() {
            return hand;
        }
    }

    static class Hand {

    }

//    @Configuration
    static class SingletonBean {
        @Bean
        public Person person() {
            return new Person(hand());
        }

        @Bean
        public Animal animal() {
            return new Animal(hand());
        }

        @Bean
        public Hand hand() {
            return new Hand();
        }
    }
}
