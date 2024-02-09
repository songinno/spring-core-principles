package hello.core;

import hello.core.Member.MemberRepository;
import hello.core.Member.MemberService;
import hello.core.Member.MemberServiceImpl;
import hello.core.Member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

@Configuration
@ComponentScan (
        /* 컴포넌트 스캔 위치 지정 */
        // Default : @ComponentScan 어노테이션을 부여한 해당 클래스의 패키지 -> hello.core
        // 탐색 패키지 지정
        basePackages = "hello.core",
        // 탐색 클래스 지정 : 지정한 클래스의 패키지를 탐색 피키지로 지정
        basePackageClasses = AutoAppConfig.class, // -> hello.core
        // 기존 설정 정보들 컴포넌트 스캔 대상에서 제외
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
