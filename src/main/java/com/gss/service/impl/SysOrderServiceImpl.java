package com.gss.service.impl;

import com.gss.entity.AliOrder;
import com.gss.entity.AliOrderExample;
import com.gss.mapper.AliOrderMapper;
import com.gss.service.SysOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysOrderServiceImpl implements SysOrderService {
    @Resource
    private AliOrderMapper orderDAO;
    @Override
    public void addOrder(AliOrder order) {
        orderDAO.insert(order);
    }

    @Override
    public void updateOrder(AliOrder order) {
        AliOrderExample aliOrderExample = new AliOrderExample();
        AliOrderExample.Criteria criteria = aliOrderExample.createCriteria();
        criteria.andOrderNumLike(order.getOrderNum());
        orderDAO.updateByExample(order,aliOrderExample);
    }

    @Override
    public AliOrder getOrderByOrderNum(String orderNum) {
        AliOrderExample aliOrderExample = new AliOrderExample();
        AliOrderExample.Criteria criteria = aliOrderExample.createCriteria();
        criteria.andOrderNumLike(orderNum);
        List<AliOrder> aliOrders = orderDAO.selectByExample(aliOrderExample);
        return aliOrders.get(0);
    }
}
