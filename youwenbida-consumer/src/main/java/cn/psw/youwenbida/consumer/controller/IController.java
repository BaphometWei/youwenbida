package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.service.IService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IController {
    @Reference
    private IService testService;

    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        return testService.sayHello("Hello springboot and dubbo!");
    }

    @RequestMapping("/index")
    public String shouye(){
        return "/pages/index.html";
    }

    @RequestMapping("/zhuye")
    public String zhuye(){
        return "/pages/user/zhuye.html";
    }
}
