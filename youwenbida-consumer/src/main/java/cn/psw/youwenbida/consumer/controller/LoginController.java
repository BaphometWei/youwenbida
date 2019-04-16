package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Admin;
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

@Controller
public class LoginController {

    @Reference
    IdentityService identityService;

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")!=null)
            return "/pages/index.html";
        return "/pages/login.html";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseBo doLogin(HttpServletRequest request, User user) throws Exception{
        HttpSession session = request.getSession();
        user.setPassword(AesUtil.encrypt(user.getPassword()));
        if(identityService.doLogin(user)!=null) {
            //把用户数据保存在session域对象中
            session.setAttribute("username", identityService.doLogin(user).getName());
            session.setAttribute("userid", identityService.doLogin(user).getId());
            return ResponseBo.ok();
        }
        else {
            return new ResponseBo().put("code","1").put("msg","用户名或密码错误");
        }
    }
    
    @RequestMapping("/admin-doLogin")
    @ResponseBody
    public ResponseBo admindoLogin(HttpServletRequest request, Admin admin) throws Exception{
        HttpSession session = request.getSession();
        admin.setAdminpassword(AesUtil.encrypt(admin.getAdminpassword()));
        if(identityService.doLogin(admin)!=null) {
            //把用户数据保存在session域对象中
            session.setAttribute("adminname", identityService.doLogin(admin).getAdminname());
            session.setAttribute("adminid", identityService.doLogin(admin).getId());
            return ResponseBo.ok();
        }
        else {
            return new ResponseBo().put("code","1").put("msg","用户名或密码错误");
        }
    }

    @RequestMapping("/dlzt")
    @ResponseBody
    public ResponseBo dlzt(HttpServletRequest request){
        HttpSession session =request.getSession();
        if(session.getAttribute("userid")!=null) {
            User user = identityService.getUser((String) session.getAttribute("userid"));
            return ResponseBo.ok().put("user", user);
        }
        return ResponseBo.error();
    }

}
