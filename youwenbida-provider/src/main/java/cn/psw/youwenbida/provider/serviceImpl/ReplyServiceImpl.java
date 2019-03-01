package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Reply;
import cn.psw.youwenbida.api.service.ReplyService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.ReplyMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper replyMapper;

    public ResponseBo insComRe(Reply reply, String uid){
        reply.setRz(uid);
        if(replyMapper.insert(reply)!=0)
            return new ResponseBo().ok().put("msg","回复成功！");
        else
            return new ResponseBo().error();
    }

    public List<Reply> getRe(Integer cid){
        Reply reply = new Reply();
        reply.setRbc(cid);
        return replyMapper.selectByPrimaryKey(reply);
    }
}
