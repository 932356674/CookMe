package com.gss.service;

import com.gss.entity.AliOrder;
import com.gss.entity.Product;

import java.util.List;


public interface SysOrderService {

    //新增订单信息
    void addOrder(AliOrder order);

    //修改订单信息
    void updateOrder(AliOrder order);

    //根据订单号查询订单信息
    AliOrder getOrderByOrderNum(String orderNum);

    //根据连接号查询商品ID
    List<Product> getProByAliNum(Long aliItem);



}


