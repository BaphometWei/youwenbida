package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Topic;
import cn.psw.youwenbida.api.service.*;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TopicController {

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


    @RequestMapping("/topicsquare")
    public String topicsquare(){
        return "/pages/topicsquare.html";
    }

    @RequestMapping("/topic")
    public String topic(){
        return "/pages/topic.html";
    }

    @RequestMapping("/toTopic")
    public String toTopic(String id){
        return "redirect:/topic?tid="+id;
    }

    @RequestMapping("/getTopic")
    @ResponseBody
    public ResponseBo getTopic(){
        List<Topic> topics = topicService.getAllTopic();
        for(Topic topic:topics)
            topic.setTjj(topic.getTjj().replace("<p>","").replace("</p>","").replace("<br>",""));
        return ResponseBo.ok().put("topics",topics);
    }

    @RequestMapping("/getTopicById")
    @ResponseBody
    public ResponseBo getTopicById(@RequestBody @RequestParam("tid")String tid){
        Topic topic = topicService.getTopicById(tid);
        String jj = topic.getTjj().replace("<p>","").replace("</p>","").replace("<br>","");
        return ResponseBo.ok().put("topic",topic).put("jj",jj);
    }

}
