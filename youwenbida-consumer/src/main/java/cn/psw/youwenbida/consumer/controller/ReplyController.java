package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Reply;
import cn.psw.youwenbida.api.service.ReplyService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    @Reference
    ReplyService replyService;

    @RequestMapping("/insComRe")
    @ResponseBody
    public ResponseBo insComRe(HttpServletRequest request, Reply reply){
        HttpSession session = request.getSession();
        return replyService.insComRe(reply,(String)session.getAttribute("userid"));
    }
}
