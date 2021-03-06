package cn.psw.youwenbida.consumer.controller;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.psw.youwenbida.api.model.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.psw.youwenbida.api.model.Answer;
import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.AnswerService;
import cn.psw.youwenbida.api.service.CommentService;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.service.NoticeService;
import cn.psw.youwenbida.api.service.OperationService;
import cn.psw.youwenbida.api.service.ProblemService;
import cn.psw.youwenbida.api.service.TopicService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.consumer.utils.Lucence;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

	@Reference(timeout = 5000, retries = 0)
	AnswerService answerService;
	@Reference(timeout = 5000, retries = 0)
	OperationService operationService;
	@Reference(timeout = 5000, retries = 0)
	ProblemService problemService;
	@Reference(timeout = 5000, retries = 0)
	IdentityService identityService;
	@Reference(timeout = 5000, retries = 0)
	CommentService commentService;
	@Reference(timeout = 5000, retries = 0)
	TopicService topicService;
	@Reference(timeout = 5000, retries = 0)
	NoticeService noticeService;

	@RequestMapping("/admin-index")
	public String admsy(HttpServletRequest request) {
		HttpSession session = request.getSession();
        if((String)session.getAttribute("adminid")!=null)
            return "/pages/admin/index.html";
		return "/pages/admin/login.html";
	}

	@RequestMapping("/admin-login")
	public String adminlogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        if((String)session.getAttribute("adminid")!=null)
            return "/pages/admin/index.html";
		return "/pages/admin/login.html";
	}

	@RequestMapping("/admin-addTopic")
	public String adminaddTopic(HttpServletRequest request){
		return "/pages/admin/addTopic.html";
	}

	@RequestMapping("/admin-getUserOp")
	@ResponseBody
	public ResponseBo admingetUserOp(@RequestBody @RequestParam("op") String op, @RequestParam("time") String time)
			throws ParseException {
		List<User> users = new ArrayList<>();
		List<Map<String, Object>> list = new ArrayList<>();
		String uid = null;
		if (op.equals("1")) {
			list = problemService.getUserByDate(time);
			uid = "ptcz";
		} else {
			list = answerService.getUserByDate(time);
			uid = "ahdz";
		}
		for (Map<String, Object> map : list) {
			User user = identityService.getUser((String) map.get(uid));
			user.setTwsl(problemService.getCountUserPro(user.getId(), time));
			user.setHdsl(answerService.getCountUserAns(user.getId(), time));
			users.add(user);
		}
		return ResponseBo.ok().put("op", op).put("time", time).put("users", users);
	}

	@RequestMapping("/admin-searchUser")
	@ResponseBody
	public ResponseBo searchUser(HttpServletRequest request, @RequestBody @RequestParam("name") String name)
			throws Exception {
		List<Map<String, Object>> list = Lucence.showSearchResults(name, "user");
		List<User> users = new ArrayList<>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				User user = identityService.getUser((String) map.get("id"));
				user.setName((String) map.get("text"));
				user.setTwsl(problemService.getCountUserPro(user.getId(), "10000"));
				user.setHdsl(answerService.getCountUserAns(user.getId(), "10000"));
				users.add(user);
			}
		}
		return ResponseBo.ok().put("users", users);
	}

	@RequestMapping("/admin-searchUserTw")
	@ResponseBody
	public ResponseBo searchUserTw(HttpServletRequest request, @RequestBody @RequestParam("name") String name)
			throws Exception {
		List<Map<String, Object>> list = Lucence.showSearchResults(name, "user");
		List<Problem> pros = new ArrayList<>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				User user = identityService.getUser((String) map.get("id"));
				user.setName((String) map.get("text"));
				List<Problem> problems = problemService.getProList(user.getId());
				for (Problem problem : problems) {
					problem.setUser(user);
					problem.setPhdsl(answerService.getCountAns(problem.getPid()));
					problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(), "3"));
				}
				pros.addAll(problems);
			}
		}
		return ResponseBo.ok().put("pros", pros);
	}

	@RequestMapping("/admin-searchTw")
	@ResponseBody
	public ResponseBo searchTw(HttpServletRequest request, @RequestBody @RequestParam("name") String name)
			throws Exception {
		List<Map<String, Object>> list = Lucence.showSearchResults(name, "pro");
		List<Problem> pros = new ArrayList<>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				Problem problem = problemService.getPro(Integer.parseInt((String) map.get("id")));
				problem.setPtitle((String) map.get("text"));
				problem.setUser(identityService.getUser(problem.getPtcz()));
				problem.setPhdsl(answerService.getCountAns(problem.getPid()));
				problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(), "3"));
				pros.add(problem);
			}
		}
		return ResponseBo.ok().put("pros", pros);
	}

	@RequestMapping("/admin-getTw")
	@ResponseBody
	public ResponseBo admingetTw(@RequestBody @RequestParam("op") String op, @RequestParam("time") String time)
			throws ParseException {
		List<Problem> pros = new ArrayList<>();
		List<Map<String, Object>> list = new ArrayList<>();
		String pid = null;
		if (op.equals("1")) {
			list = answerService.getTwHdByDate(time);
			pid = "ahdwt";
		} else {
			list = operationService.getProGzByDate(time);
			pid = "obo";
		}
		Problem problem = new Problem();
		for (Map<String, Object> map : list) {
			if (op.equals("2"))
				problem = problemService.getPro(Integer.parseInt((String) map.get(pid)));
			else
				problem = problemService.getPro((Integer) map.get(pid));
			if(problem!=null) {
			problem.setUser(identityService.getUser(problem.getPtcz()));
			problem.setPhdsl(answerService.getCountAns(problem.getPid()));
			problem.setPgzzsl(operationService.getOpConut(problem.getPid().toString(), "3"));
			pros.add(problem);
			}
		}
		return ResponseBo.ok().put("op", op).put("time", time).put("pros", pros);
	}

	@RequestMapping("/admin-getHd")
	@ResponseBody
	public ResponseBo admingetHd(@RequestBody @RequestParam("op") String op, @RequestParam("time") String time)
			throws ParseException {
		List<Answer> answers = new ArrayList<>();
		List<Map<String, Object>> list = new ArrayList<>();
		String aid = null;
		if (op.equals("1")) {
			list = operationService.getHdZanByDate(time);
			aid = "obo";
		}
		if (op.equals("2")) {
			list = operationService.getHdCaiByDate(time);
			aid = "obo";
		}
		if (op.equals("3")) {
			list = operationService.getHdScByDate(time);
			aid = "obo";
		}
		Answer answer = new Answer();
		for (Map<String, Object> map : list) {
			answer = answerService.getAns(Integer.parseInt((String) map.get("obo")));
			if(answer!=null) {
			answer.setProblem(problemService.getPro(answer.getAhdwt()));
			answer.setUser(identityService.getUser(answer.getAhdz()));
			answer.setAztsl(operationService.getOpConut(answer.getAid().toString(), "1"));
			answer.setAfdsl(operationService.getOpConut(answer.getAid().toString(), "2"));
			answer.setAscsl(operationService.getOpConut(answer.getAid().toString(), "6"));
			answers.add(answer);
			}
		}
		return ResponseBo.ok().put("op", op).put("time", time).put("answers", answers);
	}

	@RequestMapping("/admin-searchHdByUser")
	@ResponseBody
	public ResponseBo searchHdByUser(HttpServletRequest request, @RequestBody @RequestParam("name") String name)
			throws Exception {
		List<Map<String, Object>> list = Lucence.showSearchResults(name, "user");
		List<Answer> answers = new ArrayList<>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				User user = identityService.getUser((String) map.get("id"));
				user.setName((String) map.get("text"));
				List<Answer> ans = answerService.getUserAns(user.getId());
				for (Answer answer : ans) {
					answer.setProblem(problemService.getPro(answer.getAhdwt()));
					answer.setUser(user);
					answer.setAztsl(operationService.getOpConut(answer.getAid().toString(), "1"));
					answer.setAfdsl(operationService.getOpConut(answer.getAid().toString(), "2"));
					answer.setAscsl(operationService.getOpConut(answer.getAid().toString(), "6"));
				}
				answers.addAll(ans);
			}
		}
		return ResponseBo.ok().put("answers", answers);
	}

	@RequestMapping("/admin-getChart")
	@ResponseBody
	public ResponseBo getChart(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		String date[] = new String[12];
		long hd[] = new long[12];
		long tw[] = new long[12];
		long pl[] = new long[12];
		list = answerService.getHdCountByMonth();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			date[11 - i] = (String) map.get("month");
			hd[11 - i] = ((long) map.get("count"));
		}
		list = problemService.getTwCountByMonth();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			tw[11 - i] = (long) map.get("count");
		}
		list = commentService.getPlCountByMonth();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			pl[11 - i] = (long) map.get("count");
		}
		return ResponseBo.ok().put("date", date).put("hd", hd).put("tw", tw).put("pl", pl);
	}

	@RequestMapping("/admin-delete")
    @ResponseBody
    public ResponseBo admindelete(HttpServletRequest request, @RequestBody @RequestParam("lx") String lx,@RequestParam("alt") String alt) throws Exception {
		int code=1;
		if(lx.equals("hd")) {
			code =(Integer) answerService.deleteAns(alt).get("code");
		}
		if(lx.equals("tw")) {
			code =(Integer) problemService.deletePro(alt).get("code");
		}
		if(lx.equals("topic")) {
			code =(Integer) topicService.delete(Integer.parseInt(alt)).get("code");
		}
		if(code==0)
			return ResponseBo.ok().put("msg","删除成功");
		return ResponseBo.error().put("msg", "删除失败");
	}
	
	@RequestMapping("/admin-getTopic")
	@ResponseBody
	public ResponseBo admingetTopic(HttpServletRequest request) {
		return ResponseBo.ok().put("topics", topicService.getAllTopic());
	}

	@RequestMapping("/addTopic")
	@ResponseBody
	public ResponseBo addTopic(Topic topic){
		int insert = topicService.insertTopic(topic);
		if(insert==0){
			return ResponseBo.error();
		}
		return ResponseBo.ok();
	}

	//资源上传
	@RequestMapping("/topic_upload")
	@ResponseBody
	public ResponseBo res_upload(HttpServletRequest request, @RequestBody @RequestParam("file") MultipartFile file) throws Exception{
		String path = System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/image/topic/";
		String entryName = file.getOriginalFilename();
		String indexName=entryName.substring(entryName.lastIndexOf("."));
		String fileName = String.valueOf(UUID.randomUUID());
		String fileurl ="";
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
		BufferedImage img = ImageIO.read(new File(System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/image/topic/"+fileName + indexName));
		if(img.getWidth()>=550){
			int imageWidth = img.getWidth();
			int imageHeight = img.getHeight();

			double scale = (double) 600 / imageWidth;

			//计算等比例压缩之后的狂傲
			int newWidth = (int) (imageWidth * scale);
			int newHeight = (int) (imageHeight * scale);
			BufferedImage newImage = scaleImage(img, scale, newWidth, newHeight);

			File file_out = new File(System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/image/topic/"+fileName + indexName);
			ImageIO.write(newImage, "jpg", file_out);
			fileurl = "/images/topic/"+fileName + indexName;
		}
		return ResponseBo.ok().put("file",fileName + indexName);
	}

	public BufferedImage scaleImage(BufferedImage bufferedImage, double scale, int width, int height) {
		int imageWidth = bufferedImage.getWidth();
		int imageHeight = bufferedImage.getHeight();
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(bufferedImage, new BufferedImage(width, height, bufferedImage.getType()));
	}
}
