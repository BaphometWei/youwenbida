package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Operation;
import cn.psw.youwenbida.api.utils.ResponseBo;

import java.util.List;
import java.util.Map;

public interface OperationService {

    public Operation getOplx(Operation operation);

    public Operation getOp(String uid,String id,String op);

    public List<Operation> getOpList(String uid, String id, String op);

    public List<Operation> getUserOp(String uid, String id, String op);

    public Integer getOpConut(String id,String lx);

    public ResponseBo op(String uid,String id,String op);

    public ResponseBo deleteop(String uid,String id,String op);

    List<Map<String,Object>> getProGzByDate(String date);

    List<Map<String,Object>> getHdZanByDate(String date);

    List<Map<String,Object>> getHdCaiByDate(String date);

    List<Map<String,Object>> getHdScByDate(String date);

}
