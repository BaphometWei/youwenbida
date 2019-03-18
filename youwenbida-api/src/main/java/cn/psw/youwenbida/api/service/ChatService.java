package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Chat;

import java.util.List;

public interface ChatService {

    Chat insertChat(Chat chat);

    List<Chat> getChat(Chat chat);

    int deleteChat(Integer chid);

}
