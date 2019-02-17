package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.utils.AesUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class SignupController {

    @Reference
    IdentityService identityService;

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/signup")
    public String login(){
        return "/pages/signup.html";
    }

    @RequestMapping("/doSignup")
    public String doSignup(User user){
        user.setPassword(AesUtil.encrypt(user.getPassword()));
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        if(identityService.doSignup(user).get("code").toString().equals("0"))
            return "redirect:/login?signup=0";
        else
            return "redirect:/signup";
    }
}
