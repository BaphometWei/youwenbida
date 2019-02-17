package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.utils.AesUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Reference
    IdentityService identityService;

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login(){
        return "/pages/login.html";
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, User user){
        HttpSession session = request.getSession();
        user.setPassword(AesUtil.encrypt(user.getPassword()));
        if(identityService.doLogin(user).get("code").toString().equals("0")) {
            //把用户数据保存在session域对象中
            session.setAttribute("user", user.getName());
            return "redirect:index";
        }
        else
            return "redirect:login";
    }

    @RequestMapping("/dlzt")
    @ResponseBody
    public String dlzt(HttpServletRequest request){
        HttpSession session =request.getSession();
        if(session.getAttribute("user")==null)
            return "false";
        return "true";
    }

}
