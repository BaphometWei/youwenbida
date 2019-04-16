package cn.psw.youwenbida.consumer.controller;

import cn.psw.youwenbida.api.model.*;
import cn.psw.youwenbida.api.service.*;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.consumer.utils.Lucence;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
public class UserController {

    @Reference(timeout = 5000,retries = 0)
    CommentService commentService;
    @Reference(timeout = 5000,retries = 0)
    IdentityService identityService;
    @Reference(timeout = 5000,retries = 0)
    OperationService operationService;
    @Reference(timeout = 5000,retries = 0)
    ReplyService replyService;
    @Reference(timeout = 5000,retries = 0)
    NoticeService noticeService;
    @Reference(timeout = 5000,retries = 0)
    ProblemService problemService;
    @Reference(timeout = 5000,retries = 0)
    AnswerService answerService;
    @Reference(timeout = 5000,retries = 0)
    ChatService chatService;


    @RequestMapping("/index")
    public String shouye(){
        return "/pages/index.html";
    }

    @RequestMapping("/zhuye")
    public String zhuye(){
        return "/pages/user/zhuye.html";
    }

    @RequestMapping("/updatexx")
    public String updatexx(HttpServletRequest request){
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")==null)
            return "/pages/admin/404.html";
        return "/pages/user/updatexx.html";
    }


    @RequestMapping("/getUser")
    @ResponseBody
    public ResponseBo getUser(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        User user = identityService.getUser(uid);
        HttpSession session = request.getSession();
        String br = "false", show1 = "false", show2 = "false";
        if (user.getId().equals((String) session.getAttribute("userid")))
            br = "true";
        else if ((String) session.getAttribute("userid") != null) {
            if (operationService.getOp((String) session.getAttribute("userid"), uid, "5") != null)
                user.setIsgz(true);
        }
        if (user.getIndustry() != null || user.getGs() != null) {
            show1 = "true";
        }
        if (user.getEducation() != null || user.getLocation() != null || user.getIntroduction() != null || user.getIndustry() != null || user.getGs() != null) {
            show2 = "true";
        }
        return ResponseBo.ok().put("user", user).put("br", br).put("show1", show1).put("show2", show2);
    }

    @RequestMapping("/gz")
    @ResponseBody
    public ResponseBo gz(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        if((Integer)operationService.op((String) session.getAttribute("userid"), uid, "5").get("code")==0){
            Notice notice = new Notice();
            notice.setNlx("<i class='layui-icon layui-icon-component'></i>");
            notice.setNnr("<a target='_blank' href='/zhuye?id="+(String)session.getAttribute("userid")+"'> "+(String)session.getAttribute("username")+" </a>关注了你");
            notice.setNz(uid);
            notice.setNdate(new Date());
            noticeService.insertNotice(notice);
            return ResponseBo.ok();
        }
        return ResponseBo.error();
    }

    @RequestMapping("/deleteGz")
    @ResponseBody
    public ResponseBo deleteGz(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        return operationService.deleteop((String) session.getAttribute("userid"), uid, "5");
    }

    @RequestMapping("/getUserOp")
    @ResponseBody
    public ResponseBo getUserOp(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        List<Operation> operations = operationService.getUserOp(uid, null, "('1','2','3')");
        List<Operation> operationsed = new ArrayList<Operation>();
        uid = (String) session.getAttribute("userid");
        for (Operation operation : operations) {
            if (operation.getOlx().equals("1") || operation.getOlx().equals("2")) {
                Answer answer = answerService.getAns(Integer.parseInt(operation.getObo()));
                if(answer != null) {
                    User user = new User();
                    if(answer.getNm().equals("t")) {
                        if(uid!=null&&answer.getAhd().equals(uid)) {
                            user.setName("匿名用户(我)");
                            user.setId("###");
                            user.setImg("/img/niming.jpg");
                        }else{
                            user.setName("匿名用户");
                            user.setId("###");
                            user.setImg("/img/niming.jpg");
                        }
                    }
                    if(answer.getNm().equals("f")) {
                        user = identityService.getUser(answer.getAhdz());
                    }
                    if(user.getGxqm()==null)
                        user.setGxqm("");
                    answer.setUser(user);
                    answer.setProblem(problemService.getPro(answer.getAhdwt()));
                    if (uid != null) {
                        if (operationService.getOp(uid, answer.getAid().toString(), "1") != null)
                            answer.setDz(true);
                        if (operationService.getOp(uid, answer.getAid().toString(), "2") != null)
                            answer.setSc(true);
                        if (operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                            answer.setFd(true);
                    }
                    answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
                    answer.setAztsl(operationService.getOpConut(answer.getAid().toString(), "1"));
                    operation.setAnswer(answer);
                    operationsed.add(operation);
                }
            }
            if (operation.getOlx().equals("3")) {
            	Problem problem= problemService.getPro(Integer.parseInt(operation.getObo()));
            	if(problem!=null) {
            		operation.setProblem(problemService.getPro(Integer.parseInt(operation.getObo())));
            		operationsed.add(operation);
            	}
            }
        }
        return ResponseBo.ok().put("ops", operationsed);
    }

    @RequestMapping("/getUserAns")
    @ResponseBody
    public ResponseBo getUserAns(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        boolean br =false;
        if(uid.equals((String) session.getAttribute("userid")))
            br =true;
        List<Answer> answers = answerService.getUserAns(uid);
        uid = (String) session.getAttribute("userid");
        for (Answer answer : answers) {
            User user = identityService.getUser(answer.getAhdz());
            if (user.getGxqm() == null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if (uid != null) {
                if (operationService.getOp(uid, answer.getAid().toString(), "1") != null)
                    answer.setDz(true);
                if (operationService.getOp(uid, answer.getAid().toString(), "2") != null)
                    answer.setSc(true);
                if (operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
        }
        if(br==false){
            Iterator<Answer> it = answers.iterator();
            while(it.hasNext()){
                Answer x = it.next();
                if(x.getNm().equals("t")){
                    it.remove();
                }
            }
        }
        return ResponseBo.ok().put("ans", answers).put("br",br);
    }

    @RequestMapping("/getUserSc")
    @ResponseBody
    public ResponseBo getUserSc(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        List<Operation> operations = operationService.getOpList(uid, null, "2");
        List<Operation> operationsed = new ArrayList<Operation>();
        HttpSession session = request.getSession();
        uid = (String) session.getAttribute("userid");
        for (Operation operation : operations) {
            Answer answer = answerService.getAns(Integer.parseInt(operation.getObo()));
            if(answer!=null) {
            User user = new User();
            if(answer.getNm().equals("t")) {
                if(uid!=null&&answer.getAhd().equals(uid)) {
                    user.setName("匿名用户(我)");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }else{
                    user.setName("匿名用户");
                    user.setId("###");
                    user.setImg("/img/niming.jpg");
                }
            }
            if(answer.getNm().equals("f")) {
                user = identityService.getUser(answer.getAhdz());
            }
            if(user.getGxqm()==null)
                user.setGxqm("");
            answer.setUser(user);
            answer.setProblem(problemService.getPro(answer.getAhdwt()));
            if (uid != null) {
                if (operationService.getOp(uid, answer.getAid().toString(), "1") != null)
                    answer.setDz(true);
                if (operationService.getOp(uid, answer.getAid().toString(), "2") != null)
                    answer.setSc(true);
                if (operationService.getOp(uid, answer.getAid().toString(), "6") != null)
                    answer.setFd(true);
            }
            answer.setAplsl(commentService.getAnsComCount(answer.getAid()));
            answer.setAztsl(operationService.getOpConut(answer.getAid().toString(),"1"));
            operation.setAnswer(answer);
            operationsed.add(operation);
        }
        }
        return ResponseBo.ok().put("ops", operationsed);
    }

    @RequestMapping("/getUserPro")
    @ResponseBody
    public ResponseBo getUserPro(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid) {
        HttpSession session = request.getSession();
        boolean br =false;
        if(uid.equals((String) session.getAttribute("userid")))
            br =true;
        List<Problem> problems = problemService.getProList(uid);
        for(Problem problem:problems){
            problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(),"3"));
            problem.setPhdsl(answerService.getCountAns(problem.getPid()));
        }
        return ResponseBo.ok().put("pros",problems).put("br",br);

    }

    @RequestMapping("/getUserGz")
    @ResponseBody
    public ResponseBo getUserGz(HttpServletRequest request,@RequestBody @RequestParam("uid")String uid){
        List<Operation> operations = operationService.getOpList(uid,null,"5");
        List<User> users = new ArrayList<>();
        HttpSession session = request.getSession();
        uid = (String) session.getAttribute("userid");
        for(Operation operation:operations){
            User user = identityService.getUser(operation.getObo());
            user.setTwsl(problemService.getCountUserPro(user.getId()));
            user.setHdsl(answerService.getCountUserAns(user.getId()));
            user.setGzzsl(operationService.getOpConut(user.getId(),"5"));
            if(uid!=null)
                if (operationService.getOp(uid, user.getId(), "5") != null)
                    user.setIsgz(true);
            users.add(user);
        }
        return ResponseBo.ok().put("users",users);
    }

    @RequestMapping("/getGzUser")
    @ResponseBody
    public ResponseBo getGzUser(HttpServletRequest request,@RequestBody @RequestParam("uid")String uid){
        List<Operation> operations = operationService.getOpList(null,uid,"5");
        List<User> users = new ArrayList<>();
        HttpSession session = request.getSession();
        uid = (String) session.getAttribute("userid");
        for(Operation operation:operations){
            User user = identityService.getUser(operation.getObo());
            user.setTwsl(problemService.getCountUserPro(user.getId()));
            user.setHdsl(answerService.getCountUserAns(user.getId()));
            user.setGzzsl(operationService.getOpConut(user.getId(),"5"));
            if(uid!=null)
                if (operationService.getOp(uid, user.getId(), "5") != null)
                    user.setIsgz(true);
            users.add(user);
        }
        return ResponseBo.ok().put("users",users);
    }

    @RequestMapping("/getGzTw")
    @ResponseBody
    public ResponseBo getGzTw(HttpServletRequest request,@RequestBody @RequestParam("uid")String uid){
        HttpSession session = request.getSession();
        List<Operation> operations = operationService.getOpList(uid,null,"3");
        List<Problem> pros = new ArrayList<>();
        uid = (String) session.getAttribute("userid");
        for(Operation operation:operations){
            Problem problem = problemService.getPro(Integer.parseInt(operation.getObo()));
            if(problem!=null) {
            problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(),"3"));
            problem.setPhdsl(answerService.getCountAns(problem.getPid()));
            pros.add(problem);
            }
        }
        return ResponseBo.ok().put("pros",pros);
    }

    @RequestMapping("/updateuserxx")
    @ResponseBody
    public ResponseBo updateuserxx(HttpServletRequest request){
        User user = new User();
        HttpSession session = request.getSession();
        user.setId((String) session.getAttribute("userid"));
        return identityService.updateUserXx(user);
    }

    @RequestMapping("/getDlyh")
    @ResponseBody
    public ResponseBo getDlyh(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = identityService.getUser((String)session.getAttribute("userid"));
        if(user.getGxqm()==null)
            user.setGxqm("");
        return ResponseBo.ok().put("user",user);
    }

    @RequestMapping("/tixing")
    public String tixing(HttpServletRequest request){
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")==null)
            return "/pages/admin/404.html";
        return "/pages/tixing.html";
    }

    @RequestMapping("/xiesixin")
    public String xiesixin(HttpServletRequest request){
        HttpSession session = request.getSession();
        if((String)session.getAttribute("userid")==null)
            return "/pages/admin/404.html";
        return "/pages/chat.html";
    }



    @RequestMapping("/getNotice")
    @ResponseBody
    public ResponseBo getNotice(HttpServletRequest request){
        HttpSession session = request.getSession();
        return noticeService.getNoticeByNz((String)session.getAttribute("userid"));
    }

    @RequestMapping("/getWdCount")
    @ResponseBody
    public ResponseBo getWdCount(HttpServletRequest request){
        HttpSession session = request.getSession();
        return ResponseBo.ok().put("wdntc",noticeService.getWdNoticeCount((String)session.getAttribute("userid"))).put("userid",(String)session.getAttribute("userid"));
    }

    @RequestMapping("/chat")
    @ResponseBody
    public ResponseBo chat(HttpServletRequest request,Chat chat){
        HttpSession session = request.getSession();
        chat.setCz((String)session.getAttribute("userid"));
        chat = chatService.insertChat(chat);
        if(chat!=null) {
            Notice notice = new Notice();
            notice.setNlx("<i class='layui-icon layui-icon-reply-fill'></i>");
            notice.setNnr("<a target='_blank' href='/zhuye?id="+(String)session.getAttribute("userid")+"'> "+(String)session.getAttribute("username")+" </a>向你发送了 <a target='_blank' href='xiesixin?uid="+chat.getCz()+"'>一条私信</a>");
            notice.setNz(chat.getCbz());
            notice.setNdate(new Date());
            noticeService.insertNotice(notice);
            return ResponseBo.ok().put("chat", chat);
        }
        return ResponseBo.error();
    }

    @RequestMapping("/getChat")
    @ResponseBody
    public ResponseBo getChat(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid){
        HttpSession session = request.getSession();
        User chatuser = identityService.getUser(uid);
        User user = identityService.getUser((String)session.getAttribute("userid"));
        Chat chat = new Chat();
        chat.setCz((String)session.getAttribute("userid"));
        chat.setCbz(uid);
        return ResponseBo.ok().put("user",user).put("chatuser",chatuser).put("chat",chatService.getChat(chat));
    }

    @RequestMapping("/deleteChat")
    @ResponseBody
    public ResponseBo deleteChat(HttpServletRequest request, @RequestBody @RequestParam("chid") String chid){
        if(chatService.deleteChat(Integer.parseInt(chid))!=0){
            return ResponseBo.ok();
        }
        return ResponseBo.error();
    }

    @RequestMapping("/selectRandUser")
    @ResponseBody
    public ResponseBo selectRandUser(HttpServletRequest request){
        return identityService.selectRand();
    }

    @RequestMapping("/yqhd")
    @ResponseBody
    public ResponseBo yqhd(HttpServletRequest request, @RequestBody @RequestParam("uid") String uid,@RequestParam("pid") String pid){
        HttpSession session = request.getSession();
        Notice notice = new Notice();
        notice.setNlx("<i class='layui-icon layui-icon-edit'></i>");
        Problem problem = problemService.getPro(Integer.parseInt(pid));
        notice.setNnr("<a target='_blank' href='/zhuye?id="+(String)session.getAttribute("userid")+"'> "+(String)session.getAttribute("username")+" </a>邀请你回答 <a target='_blank' href='problem?proid="+problem.getPid()+"'> "+problem.getPtitle()+"</a>");
        notice.setNz(uid);
        notice.setNdate(new Date());
        noticeService.insertNotice(notice);
        return ResponseBo.ok();
    }

    @RequestMapping("/oninputSearchUser")
    @ResponseBody
    public ResponseBo oninputSearchUser(HttpServletRequest request, @RequestBody @RequestParam("q") String q) throws Exception{
        List<Map<String,Object>> list = Lucence.showSearchResults(q,"user");
        return ResponseBo.ok().put("users",list);
    }

    @RequestMapping(value ="/userimgupload", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Map<String,Object> imgupload(HttpServletRequest request,@RequestBody @RequestParam("image") String image) throws Exception{
        String base64 = image.substring(image.indexOf(",") + 1);
        String path = "G:/eclipseproject/youwenbida/youwenbida-consumer/src/main/resources/image/user/";
        String imgName = UUID.randomUUID() + ".png";
        FileOutputStream write = new FileOutputStream(new File(path + imgName));
        byte[] decoderBytes = Base64.getDecoder().decode(base64);
        write.write(decoderBytes);
        write.close();
        String fileurl = "/images/user/"+imgName;
        HttpSession session = request.getSession();
        User user = new User();
        user.setId((String) session.getAttribute("userid"));
        user.setImg(fileurl);
        return identityService.updateUserXx(user).put("result","ok").put("file",fileurl);
    }
}
