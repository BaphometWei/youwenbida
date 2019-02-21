package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.utils.ResponseBo;

public interface ProblemService {

    public Problem getPro(Integer id);

    public ResponseBo insertPro(Problem problem);
}
