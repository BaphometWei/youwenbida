package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Problem;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;

public interface ProblemService {

    Problem getPro(Integer id);

    ResponseBo insertPro(Problem problem);

    List<Problem> getProList(String uid);

    Integer getCountUserPro(String uid);

    ResponseBo updatePro(Problem problem);

    ResponseBo deletePro(String pid);
}
