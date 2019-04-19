package com.gss.service.impl;

import com.gss.entity.*;
import com.gss.mapper.AliOrderMapper;
import com.gss.mapper.OrderItemsMapper;
import com.gss.mapper.ProductMapper;
import com.gss.service.SysOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysOrderServiceImpl implements SysOrderService {
    @Resource
    private AliOrderMapper orderDAO;

    @Resource
    private OrderItemsMapper orderItemsMapper;

    @Resource
    private ProductMapper productMapper;

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

    @Override
    public List<Product> getProByAliNum(Long aliItem) {
        OrderItemsExample orderItemsExample = new OrderItemsExample();
        OrderItemsExample.Criteria criteria =orderItemsExample.createCriteria();
        criteria.andProItmEqualTo(aliItem);
        List<OrderItems> orderItems = orderItemsMapper.selectByExample(orderItemsExample);
        List<Product> products = new ArrayList<>();
        for (int i = 0; i<orderItems.size();i++) {
            Product product = productMapper.selectByPrimaryKey(orderItems.get(i).getProductId());
            products.add(product);
        }
        return products;
    }
}
