package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SIngletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class); // Prototype 객체 자동 의존 관계 주입을 우해 둘 다 넣어줌
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    @Test
    void twoClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class, AnotherClientBean.class);
        ClientBean clientBean = ac.getBean(ClientBean.class);
        AnotherClientBean anotherClientBean = ac.getBean(AnotherClientBean.class);

        int count1 = clientBean.logic();
        int count2 = anotherClientBean.logic();
        assertThat(count1).isEqualTo(count2);

    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean; // 생성 시점에 주입됨

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) { // 이 때 스프링 컨테이너에 프로토타입 Bean 요청 -> 주입
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount(); // 생성 시점에 주입된 prototypeBean을 사용 => 의도한 바X(프로토타입 스코프를 쓰는 이유가 없음)
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("singleton")
    static class AnotherClientBean {
        private final PrototypeBean prototypeBean; // 생성 시점에 주입됨

        @Autowired
        public AnotherClientBean(PrototypeBean prototypeBean) { // 이 때 스프링 컨테이너에 프로토타입 Bean 요청 -> 주입
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount(); // 생성 시점에 주입된 prototypeBean을 사용 => 의도한 바X(프로토타입 스코프를 쓰는 이유가 없음)
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            this.count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init :  " + this);
        }

        @PreDestroy // 어짜피 호출 안됨
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }


}
