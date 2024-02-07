package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.Member.MemberRepository;
import hello.core.Member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("memberRepository1 = " + memberRepository1);
        // memberRepository1 = hello.core.Member.MemoryMemberRepository@183e8023
        System.out.println("memberRepository2 = " + memberRepository2);
        // memberRepository2 = hello.core.Member.MemoryMemberRepository@183e8023
        System.out.println("memberRepository = " + memberRepository);
        // memberRepository = hello.core.Member.MemoryMemberRepository@183e8023

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean`s class = " + bean.getClass());
        // bean`s class = class hello.core.AppConfig$$SpringCGLIB$$0
        /*
        * [ $$SpringCGLIB$$0 ]
        * - 순수한 클래스라면, class hello.core.AppConfig -> 이렇게 출력 되어야 한다.
        * - 내가 만든 클래스가 아니라, 스프링이 CGLIB(씨지립)라는 바이트 코드 조작 라이브러리를 사용하여, AppConfig 클래스를 상속 받은, 임의의 다른 클래스를 만들고, 그 클래스를 스프링 Bean으로 등록한 것
        * - 이 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해주는 것
        *
        * - ac.getBean(AppConfig.class); 로 조회가 된 이유
        *   ㄴ AppConfig$$SpringCGLIB가 AppConfig 자체는 Bean으로 등록 안되고, AppConfig$$SpringCGLIB가 대신 등록되었고, AppConfig의 자식이므로 조회가 된 것
        *       ㄴ 부모 타입으로 조회하면, 다 끌려 나오는데 1개만 끌려 나온 이유 + AppConfig로 조회해도 조회가 된 이유
        *
        * - @Configuration 애노테이션을 부여하지 않으면?
        *   ㄴ 스프링 Bean으로 등록은 됨
        *   ㄴ class hello.core.AppConfig로 출력됨(CGLIB의 조작이 동작하지 않음)
        *       -> 싱글톤 보장 안됨
        *  */
    }
}
