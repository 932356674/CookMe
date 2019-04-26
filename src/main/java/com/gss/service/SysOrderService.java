package com.gss.service;

import com.gss.entity.*;
import com.gss.utils.R;

import java.util.List;


public interface SysOrderService {
    void addOrder(AliOrder order);
    void updateOrder(AliOrder order);
    AliOrder getOrderByOrderNum(String orderNum);
    //新增地址信息并回显
    R insertCon(Consignee consignee);
    //新增订单详情并回显
    List<Object> findCount(List<OrderItems> list);
    //新增订单信息并回显
    List<AliOrder> findtrade(AliOrder aliOrder);
    //下拉框地址信息
    List<TpRegion2> selectParent(int parentId);
    //查询登录用户的地址信息
    R selectConsignee();
    //修改登录用户的地址信息
    R updateConsignee(Consignee consignee);
    //根据订单号查询商品信息
    List<Product> getProByAliNum(Long aliNum);

}
