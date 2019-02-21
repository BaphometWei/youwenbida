package cn.psw.youwenbida.api.service;

import cn.psw.youwenbida.api.model.Operation;

public interface OperationService {

    public Operation getOplx(Operation operation);

    public Operation getDz(String uid,Integer aid);

    public Operation getSc(String uid,Integer aid);

    public Integer getConutByDz(Integer aid);

    public Integer getCountDzByCom(Integer cid);
}
