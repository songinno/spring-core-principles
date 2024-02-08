package hello.core.scan.filter;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // TYPE -> 클래스 레벨에 해당 애노테이션을 부여함을 지정
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyExcludeComponent { // 이 애노테이션이 붙으면 컴포넌트 스캔에서 제외할 것

}
