package cn.psw.youwenbida.provider.serviceImpl;

import cn.psw.youwenbida.api.service.IService;
import cn.psw.youwenbida.provider.mapper.UserMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class IServiceImpl implements IService {

    private final static Logger logger = LoggerFactory.getLogger(IServiceImpl.class);
    @Autowired
    UserMapper userMapper;

    @Override
    public String sayHello(String name) {
        logger.info("81878789454445");
        System.out.println("Hello "+name +"!1222223");
        return "754148481";
    }
}
