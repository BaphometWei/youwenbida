package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.*;
import cn.psw.youwenbida.api.utils.ResponseBo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class AnswerController {

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


    @RequestMapping("/getHotAnswer")
    @ResponseBody
    public List<Answer> getHotAnswer(HttpServletRequest request){
        List<Answer> answers = answerService.getHotAnswer();
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Answer answer:answers){
            User user = identityService.getUser(answer.getAhdz());
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"6")!=null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        return answers;
    }

    @RequestMapping("/dzAnswer")
    @ResponseBody
    public ResponseBo dzAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid,@RequestParam("isfd")String isfd){
        HttpSession session = request.getSession();
        if(isfd.equals("true"))
            if((Integer)operationService.deleteop((String)session.getAttribute("userid"),aid,"6").get("code")!=0)
                return ResponseBo.error();
        if((Integer)operationService.op((String)session.getAttribute("userid"),aid,"1").get("code")==0)
            return answerService.setScore(aid);
        return ResponseBo.error();
    }

    @RequestMapping("/deleteDzAnswer")
    @ResponseBody
    public ResponseBo deleteDzAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        if((Integer)operationService.deleteop((String)session.getAttribute("userid"),aid,"1").get("code")==0)
            return answerService.setScore(aid);
        return ResponseBo.error();
    }

    @RequestMapping("/fdAnswer")
    @ResponseBody
    public ResponseBo fdAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid,@RequestParam("isdz")String isdz){
        HttpSession session = request.getSession();
        if(isdz.equals("true"))
            if((Integer)operationService.deleteop((String)session.getAttribute("userid"),aid,"1").get("code")!=0)
                return ResponseBo.error();
        if((Integer)operationService.op((String)session.getAttribute("userid"),aid,"6").get("code")==0)
            return answerService.setScore(aid);
        return ResponseBo.error();
    }

    @RequestMapping("/deleteFdAnswer")
    @ResponseBody
    public ResponseBo deleteFdAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        if((Integer)operationService.deleteop((String)session.getAttribute("userid"),aid,"6").get("code")==0)
            return answerService.setScore(aid);
        return ResponseBo.error();
    }

    @RequestMapping("/scAnswer")
    @ResponseBody
    public ResponseBo scAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        return operationService.op((String)session.getAttribute("userid"),aid,"2");
    }

    @RequestMapping("/deleteScAnswer")
    @ResponseBody
    public ResponseBo deleteScAnswer(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        HttpSession session = request.getSession();
        return operationService.deleteop((String)session.getAttribute("userid"),aid,"2");
    }

    @RequestMapping("/yzhdop")
    @ResponseBody
    public ResponseBo yzhdop(Answer answer){
        return answerService.updateAns(answer);
    }

    @RequestMapping("/xiehuida")
    @ResponseBody
    public ResponseBo xiehuida(HttpServletRequest request,Answer answer){
        HttpSession session = request.getSession();
        answer.setAhdz((String)session.getAttribute("userid"));
        return answerService.insAns(answer);
    }

    @RequestMapping("/getAns")
    @ResponseBody
    public ResponseBo getAns(HttpServletRequest request,@RequestBody @RequestParam("aid")String aid){
        Answer answer = answerService.getAns(Integer.parseInt(aid));
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        User user = identityService.getUser(answer.getAhdz());
        if(user.getGxqm()==null)
            user.setGxqm("");
        answer.setUser(user);
        answer.setProblem(problemService.getPro(answer.getAhdwt()));
        if(uid!=null){
            if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                answer.setDz(true);
            if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                answer.setSc(true);
            if(operationService.getOp(uid,answer.getAid().toString(),"6")!=null)
                answer.setFd(true);
        }
        answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
        answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        return ResponseBo.ok().put("ans",answer);
    }

    @RequestMapping("/imgupload")
    @ResponseBody
    public Map<String,Object> imgupload(@RequestParam("file") MultipartFile file) throws Exception{
        String path = System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/image/";
        String entryName = file.getOriginalFilename();
        String indexName=entryName.substring(entryName.lastIndexOf("."));
        String fileName = String.valueOf(UUID.randomUUID());
        File targetFile = new File(path + "/" + fileName + indexName);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage img = ImageIO.read(new File(System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/image/"+fileName + indexName));
        if(img.getWidth()>=550){
            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();

            double scale = (double) 600 / imageWidth;

            //计算等比例压缩之后的狂傲
            int newWidth = (int) (imageWidth * scale);
            int newHeight = (int) (imageHeight * scale);
            BufferedImage newImage = scaleImage(img, scale, newWidth, newHeight);

            File file_out = new File(System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/image/"+fileName + indexName);
            ImageIO.write(newImage, "jpg", file_out);
        }
        String fileurl = "/images/"+fileName + indexName;
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map.put("code", 0);//0表示成功，1失败
        map.put("msg", "上传成功");//提示消息
        map.put("data", map2);
        map2.put("src", fileurl);//图片url
        map2.put("title", "");//图片名称，这个会显示在输入框里
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/getTopicAns")
    @ResponseBody
    public List<Answer> getTopicAns(HttpServletRequest request,@RequestBody @RequestParam("tid")String tid){
        List<Answer> answers = answerService.getTopicAns(topicService.getTopicById(tid).getTname());
        List<Answer> answersByAnsDate = answerService.getTopicAnsByAnsDate(topicService.getTopicById(tid).getTname());
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Answer answer:answers){
            User user = identityService.getUser(answer.getAhdz());
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"6")!=null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        for(Answer answer:answersByAnsDate){
            User user = identityService.getUser(answer.getAhdz());
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"6")!=null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        answers.addAll(answersByAnsDate);
        return answers;
    }

    @RequestMapping("/getHotTopicAns")
    @ResponseBody
    public List<Answer> getHotTopicAns(HttpServletRequest request,@RequestBody @RequestParam("tid")String tid) {
        List<Answer> answers = answerService.getTopicAnsByAnsScore(topicService.getTopicById(tid).getTname());
        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute("userid");
        for(Answer answer:answers){
            User user = identityService.getUser(answer.getAhdz());
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if(uid!=null){
                if(operationService.getOp(uid,answer.getAid().toString(),"1")!=null)
                    answer.setDz(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"2")!=null)
                    answer.setSc(true);
                if(operationService.getOp(uid,answer.getAid().toString(),"6")!=null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        return answers;
    }


    public BufferedImage scaleImage(BufferedImage bufferedImage, double scale, int width, int height) {
        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(bufferedImage, new BufferedImage(width, height, bufferedImage.getType()));
    }
}
