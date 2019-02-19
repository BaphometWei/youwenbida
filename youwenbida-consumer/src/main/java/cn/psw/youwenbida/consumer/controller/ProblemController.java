package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.service.ProblemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProblemController {

    @Reference
    ProblemService problemService;

    @RequestMapping("/problem")
    public String problem(){
        return "/pages/problem.html";
    }

    @RequestMapping("/toPro")
    public String toPro(String id){
        return "redirect:/problem?proid="+id;
    }

    @RequestMapping("/getPro")
    @ResponseBody
    public Problem getPro(String id){
        return problemService.getPro(id);
    }



}
