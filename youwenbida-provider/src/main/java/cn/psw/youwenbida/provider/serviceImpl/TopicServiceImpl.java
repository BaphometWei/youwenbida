package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Topic;
import cn.psw.youwenbida.api.service.TopicService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.TopicMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 5000, retries = 0)
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Override
    public List<Topic> getAllTopic(){
        return topicMapper.selectAll();
    }

    @Override
    public Topic getTopicById(String tid) {
        return topicMapper.selectByPrimaryKey(Integer.parseInt(tid));
    }

    @Override
    public int insertTopic(Topic topic) {
        return topicMapper.insert(topic);
    }

    @Override
    public ResponseBo delete(int id) {
        int delete =  topicMapper.deleteByPrimaryKey(id);
        if(delete==0)
            return ResponseBo.error();
        return ResponseBo.ok();
    }
}
