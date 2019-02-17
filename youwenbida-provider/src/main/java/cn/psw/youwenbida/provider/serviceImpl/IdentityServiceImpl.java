package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.UserMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    UserMapper userMapper;

    private final static Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);


    @Override
    public ResponseBo doSignup(User user) {
        logger.info("执行注册操作");
        if(userMapper.insert(user)==0){
            return ResponseBo.ok().put("code","1").put("msg","注册失败");
        }

        return ResponseBo.ok().put("code","0").put("msg","注册成功");
    }

    @Override
    public ResponseBo doLogin(User user) {
        logger.info("执行登录操作");
        if(userMapper.validateByNameAndPassword(user)==null){
            return ResponseBo.ok().put("code","1").put("msg","登录失败");
        }

        return ResponseBo.ok().put("code","0").put("msg","登录成功");

    }
}