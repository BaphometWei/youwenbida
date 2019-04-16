package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Reply;
import cn.psw.youwenbida.api.service.ReplyService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.ReplyMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 5000, retries = 0)
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper replyMapper;

    @Override
    public ResponseBo insComRe(Reply reply, String uid){
        reply.setRz(uid);
        if(replyMapper.insert(reply)!=0)
            return new ResponseBo().ok().put("msg","回复成功！").put("rid",reply.getRid());
        else
            return new ResponseBo().error();
    }

    @Override
    public List<Reply> getRe(Integer cid){
        Reply reply = new Reply();
        reply.setRbc(cid);
        return replyMapper.selectByPrimaryKey(reply);
    }

    @Override
    public Reply getReByRid(Integer rid){
        Reply reply = new Reply();
        reply.setRid(rid);
        return replyMapper.selectByPrimaryKey(reply).get(0);
    }

    @Override
    public ResponseBo deleteRe(String rid){
        if(replyMapper.deleteByPrimaryKey(Integer.parseInt(rid))!=0)
            return ResponseBo.ok();
        return ResponseBo.error();
    }
}
