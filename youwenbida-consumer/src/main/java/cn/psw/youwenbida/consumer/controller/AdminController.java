package cn.psw.youwenbida.consumer.controller;


import cn.psw.youwenbida.api.service.*;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Reference(timeout = 5000,retries = 0)
    AnswerService answerService;
    @Reference(timeout = 5000,retries = 0)
    OperationService operationService;
    @Reference(timeout = 5000,retries = 0)
    ProblemService problemService;
    @Reference(timeout = 5000,retries = 0)
    IdentityService identityService;
    @Reference(timeout = 5000,retries = 0)
    CommentService commentService;
    @Reference(timeout = 5000,retries = 0)
    TopicService topicService;
    @Reference(timeout = 5000,retries = 0)
    NoticeService noticeService;


    @RequestMapping("/admin-index")
    public String admsy(){
        return "/pages/admin/index.html";
    }
}
