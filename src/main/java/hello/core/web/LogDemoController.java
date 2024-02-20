package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
        private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    @RequestMapping("log-demo")
    @ResponseBody // view 화면 없이, 문자 출력만을 위해 사용
    public String logDemo(HttpServletRequest request) {
        // HttpServletRequest : Java에서 제공하는 표준 Servlet 규약에 의한 HTTP reqeust 정보를 받을 수 있다.
        String requestURL = request.getRequestURL().toString();

//        MyLogger myLogger = myLoggerObjectProvider.getObject();

        myLogger.setRequestURL(requestURL);

        System.out.println("myLogger = "+ myLogger.getClass());

        myLogger.log("controller test");
        logDemoService.logic("test ID");
        return "OK";

    }
}
