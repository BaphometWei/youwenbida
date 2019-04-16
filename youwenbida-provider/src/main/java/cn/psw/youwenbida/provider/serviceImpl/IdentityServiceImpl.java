package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.model.Admin;
import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.service.IdentityService;
import cn.psw.youwenbida.api.utils.ResponseBo;
import cn.psw.youwenbida.provider.mapper.AdminMapper;
import cn.psw.youwenbida.provider.mapper.UserMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 5000, retries = 0)
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AdminMapper adminMapper;

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
    public ResponseBo validateSignup(User user) {
        logger.info("注册校验");
        if(userMapper.validateByColumn(user)==null){
            return ResponseBo.ok().put("code","0").put("msg","账号不存在，可以注册");
        }
        return ResponseBo.ok().put("code","1").put("msg","账号已存在");
    }

    @Override
    public User doLogin(User user) {
        logger.info("执行登录操作");
        return  userMapper.validateByColumn(user);
    }

    @Override
    public User getUser(String id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResponseBo updateUserXx(User user){
        if(userMapper.updateByPrimaryKey(user)==0){
            return ResponseBo.error().put("msg","修改失败");
        }
        return ResponseBo.ok().put("msg","修改成功");
    }

    @Override
    public ResponseBo selectRand(){
        List<User> users = userMapper.selectRand();
        if(users!=null)
            return ResponseBo.ok().put("users",users);
        return ResponseBo.error();
    }
    
    @Override
    public Admin doLogin(Admin admin) {
    	return adminMapper.admindl(admin);
    }

}
