package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.Member.MemberService;
import hello.core.Member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름 + 인터페이스 타입으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService = " + memberService);
        // memberService = hello.core.Member.MemberServiceImpl@67ab1c47
        System.out.println("memberService = " + memberService.getClass());
        // memberService = class hello.core.Member.MemberServiceImpl

        // 검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("이름 없이 인터페이스 타입으로 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름 + 구체 타입으로 조회")
    void findBeanByName2() {
        // 역할에 집중해야 하기 때문에 좋은 코드는 아니다.(필요한 경우에만 사용)
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 구체 타입으로 조회")
    void findBeanByType2() {
        // 역할에 집중해야 하기 때문에 좋은 코드는 아니다.(필요한 경우에만 사용)
        MemberService memberService = ac.getBean(MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("실패 테스트 - 빈 이름으로 조회 X")
    void findBeanByNameX() {
//        MemberService xxxx = ac.getBean("xxxx", MemberService.class);
        // -> org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'xxxx' available
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxx", MemberService.class));
    }
}
