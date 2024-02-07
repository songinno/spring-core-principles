package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {
    // 참고) ApplicationContext 인터페이스에는 getBeanDefinition()가 없다.(BeanDefinition을 뽑아 쓸 일이 없어서)
//    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
    /*
    * [ AnnotationConfigApplicationContext(자바 코드 방법)과의 차이점 ]
    * ㅇ Generic bean : bean에 대한 클래스 정보가 명확하게 등록이 되어 있음
    * ㅇ defined가 appConfig.xml
    * ㅇ factoryBeanName, factoryMethodName이 null
    *   - 옛날에는 다 xml로 설정을 하면서, bean에 대한 클래스 정보가 밖에 드러났다.
    *   - 그런데, 자바 코드 방법으로 바뀌면서
    *       ㄴ 스프링에 Bean을 등록하는 여러 방법들 중, 주요 방법 2가지
    *           1) 직접 스프링 빈을 컨테이너에 등록 (XML 방식)
    *           2) 팩토리 메서드를 이용하는 방법(Java 코드 방법)
    *               - ex) AppConfig에서 memberService()라는 팩토리 메서드를 통해서 제공
    *                   -> 외부에서 메서드를 호출해서 생성 되는 방식
    *               - AnnotationConfigApplicationContext으로 바꿔서 돌려보면,
    *                   ㄴ Generic bean 말고, Root bean: class에서, class 정보가 모두 null
    *                   ㄴ 대신, factoryBeanName, factoryMethodName 정보는 존재
    *                       -> 팩토리 빈에서 팩토리 메서드를 통해 생성이 된다
    * */

    @Test
    @DisplayName("Bean 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName
                + ", beanDefinition = " + beanDefinition);
            }
        }
    }
}
