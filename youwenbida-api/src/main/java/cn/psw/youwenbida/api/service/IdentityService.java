package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.User;
import cn.psw.youwenbida.api.utils.ResponseBo;

public interface IdentityService {

    public ResponseBo doLogin(User user);

    public ResponseBo doSignup(User user);
}
