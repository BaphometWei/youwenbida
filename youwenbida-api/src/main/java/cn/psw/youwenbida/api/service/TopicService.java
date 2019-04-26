package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Topic;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;

public interface TopicService{

    List<Topic> getAllTopic();

    Topic getTopicById(String tid);

    int insertTopic(Topic topic);

    ResponseBo delete(int id);
}
