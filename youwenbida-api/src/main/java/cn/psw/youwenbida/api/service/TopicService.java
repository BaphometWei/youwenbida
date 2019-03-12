package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Topic;

import java.util.List;

public interface TopicService{

    List<Topic> getAllTopic();

    Topic getTopicById(String tid);
}
