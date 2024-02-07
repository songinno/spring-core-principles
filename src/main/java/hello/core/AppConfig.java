package hello.core;

import hello.core.Member.MemberService;
import hello.core.Member.MemberServiceImpl;
import hello.core.Member.MemoryMemberRepository;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /*
        @Bean memberService() -> new MemberServiceImpl()
            -> memberRepository() -> new MemoryMemberRepository()

        @Bean orderService() -> new OrderServiceImpl()
            -> memberRepository() -> new MemoryMemberRepository()
            -> discountPolicy() -> new RateDiscountPolicy()

        * new MemoryMemberRepository()가 2번 있음
        * 이렇게 되면 싱글톤이 깨지는 것이 아닌가?
            * 테스트(ConfigurationSingletonTest) 결과, 모두 같은 memberRepository는 객체 인스턴스인 것으로 확인됨
        * 자바 코드상으로는 new 키워드로 여러번 생성되는데, 혹시 1번만 호출되나?
            * 테스트(AppConfig에 print로 찍어보기)
                - ConfigurationSingletonTest 실행 시, 기대되는 출력 값들(순서는 보장되지 않음)
                    // 스프링 컨테이너가 Bean 등록을 위해 @Bean이 붙어 있는 memberRepository() 호출
                        call - AppConfig.memberRepository
                    // memberService() 로직에서
                        call - AppConfig.memberService
                        call - AppConfig.memberRepository
                    // orderService() 로직에서
                        call - AppConfig.orderService
                        call - AppConfig.memberRepository
                - 실제 결과
                    call - AppConfig.memberRepository
                    call - AppConfig.memberService
                    call - AppConfig.orderService
    */

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call - AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        System.out.println("call - AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call - AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(), discountPolicy()
        );
    }
}
