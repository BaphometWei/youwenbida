package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.utils.AesUtil;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class SignupController {

    @Reference
    IdentityService identityService;

    private final static Logger logger = LoggerFactory.getLogger(SignupController.class);

    @RequestMapping("/signup")
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")==null)
            return "/pages/index.html";
        return "/pages/signup.html";
    }

    @RequestMapping("/doSignup")
    @ResponseBody
    public ResponseBo doSignup(User user){
        user.setPassword(AesUtil.encrypt(user.getPassword()));
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        if(identityService.doSignup(user).get("code").toString().equals("0"))
            return ResponseBo.ok();
        else
            return new ResponseBo().put("code","1").put("msg","系统发生错误");
    }

    @RequestMapping("/validateSignup")
    @ResponseBody
    public ResponseBo validateSignup(User user){
        logger.info("邮箱验证");
        return identityService.validateSignup(user);
    }
}
