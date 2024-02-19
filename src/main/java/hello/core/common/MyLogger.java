package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/* 로그 출력을 위한 클래스 */
@Component
@Scope(value = "request")
public class MyLogger {
    // uuid로 각각의 요청을 구분
    private String uuid;
    private String requestURL;

    // Bean이 생성되는 시점에는 URL을 알 수 없어서, setter로 받음
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] reqeust scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] reqeust scope bean close: " + this);
    }
}
