package com.gss.service;

import com.gss.entity.AliOrder;


public interface SysOrderService {
    void addOrder(AliOrder order);
    void updateOrder(AliOrder order);
    AliOrder getOrderByOrderNum(String orderNum);
}
