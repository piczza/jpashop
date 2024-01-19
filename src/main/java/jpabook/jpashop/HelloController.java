package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    //GetMapping: Get방식. 서버의 리소스 조회.
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!~!~");
        //hello.html로 이동!
        return "hello";
    }
}
