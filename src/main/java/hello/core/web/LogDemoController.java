package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //    private final MyLogger myLogger; // 요청이 들어오기 전(Bean이 생성 안된 상태)인데 의존 관계 주입을 받으려 함 -> Provider 사용
    private final ObjectProvider<MyLogger> myLoggerProvider;
    @RequestMapping("log-demo")
    @ResponseBody // view 화면 없이 문자열 반환하기 위함
    public String logDemo(HttpServletRequest request) {
        // HttpServletRequest : 자바에서 제공하는 표준 servlet 규약에 의한 HTTP request 정보를 받을 수 있음
        String reqeustURL = request.getRequestURL().toString();

        MyLogger myLogger = myLoggerProvider.getObject(); // 이 시점에 최초로 만들어짐
        myLogger.setRequestURL(reqeustURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }

}
