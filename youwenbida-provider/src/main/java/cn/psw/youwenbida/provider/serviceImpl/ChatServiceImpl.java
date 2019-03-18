package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Chat;
import cn.psw.youwenbida.api.service.ChatService;
import cn.psw.youwenbida.provider.mapper.ChatMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 5000,retries = 0)
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatMapper chatMapper;

    @Override
    public Chat insertChat(Chat chat){
        chatMapper.insert(chat);
//        if(chatMapper.getHaveChat(chat)==null)
//            chatMapper.insertHaveChat(chat);
//        else{
//            chat.setChdate(new Date());
//            chatMapper.updateHaveChat(chat);
//        }
        return chatMapper.selectByPrimaryKey(chat.getChid());
    }


    @Override
    public List<Chat> getChat(Chat chat) {
        return chatMapper.getChat(chat);
    }

    @Override
    public int deleteChat(Integer chid){
        return chatMapper.deleteByPrimaryKey(chid);
    }

}
