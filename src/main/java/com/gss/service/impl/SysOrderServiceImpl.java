package com.gss.service.impl;

import com.gss.entity.*;
import com.gss.mapper.*;
import com.gss.service.SysOrderService;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysOrderServiceImpl implements SysOrderService {
    @Resource
    private AliOrderMapper orderDAO;
    @Resource
    private ConsigneeMapper consigneeMapper;
    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Resource
    private AliOrderMapper aliOrderMapper;
    @Resource
    private TpRegion2Mapper tpRegion2Mapper;
    @Resource
    private ShopcarMapper shopcarMapper;
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
    public R insertCon(Consignee consignee) {
        int i=consigneeMapper.insert(consignee);
        ConsigneeExample example=new ConsigneeExample();
        ConsigneeExample.Criteria criteria=example.createCriteria();
        criteria.andConIdEqualTo(consignee.getConId());
        ShiroUtils.setAttribute("conId",consignee.getConId()+"");
        List<Consignee> list=consigneeMapper.selectByExample(example);
        return R.ok().put("list",list);
    }

    @Override
    public List<Object> findCount(List<OrderItems> list) {
        List<Object> list1=new ArrayList<Object>();
        long j=System.currentTimeMillis();
        for (OrderItems orderItems : list) {
            orderItems.setProItm(j);
            int i=orderItemsMapper.insert(orderItems);

            OrderItemsAndProduct orderItemsAndProduct=orderItemsMapper.findOrderAndPro(orderItems);
            list1.add(orderItemsAndProduct);
        }
        ShiroUtils.setAttribute("j",j+"");
        return list1;
    }

    @Override
    public List<AliOrder> findtrade(AliOrder aliOrder) {
         long orderNum=System.currentTimeMillis();
         aliOrder.setOrderNum(orderNum+"");
         ShiroUtils.setAttribute("orderNum",orderNum+"");
         aliOrder.setUsId(ShiroUtils.getUserId());
         aliOrder.setConId(Integer.valueOf(String.valueOf(ShiroUtils.getAttribute("conId"))).intValue());
         try {
             aliOrder.setAliItem(Long.valueOf(String.valueOf(ShiroUtils.getAttribute("j"))).longValue());
         }catch (NumberFormatException q){
             aliOrder.setAliItem(null);
         }
        // aliOrder.setUsId(2);
        // aliOrder.setConId(7);
        // aliOrder.setAliItem(orderNum);
        aliOrder.setCreateDate(new Date(System.currentTimeMillis()));
        int i=aliOrderMapper.insert(aliOrder);
        try{
            //生成订单删除购物车
            OrderItemsExample orderItemsExample = new OrderItemsExample();
            OrderItemsExample.Criteria criteria1 = orderItemsExample.createCriteria();
            System.out.println("*********************************************************");
            criteria1.andProItmEqualTo(Long.valueOf(String.valueOf(ShiroUtils.getAttribute("j"))).longValue());
            System.out.println("//////////////////////////////////////////////////////");
            // criteria1.andProItmEqualTo(1555598785238L);
            List<OrderItems> list1 = orderItemsMapper.selectByExample(orderItemsExample);
            for (OrderItems orderItems : list1) {
                ShopcarExample shopcarExample = new ShopcarExample();
                ShopcarExample.Criteria criteria2 = shopcarExample.createCriteria();
                criteria2.andProductIdEqualTo(orderItems.getProductId());
                List<Shopcar> list2 = shopcarMapper.selectByExample(shopcarExample);
                for (Shopcar shopcar : list2) {
                    shopcar.setUsId(ShiroUtils.getUserId());
                    //  shopcar.setUsId(1);
                    int ddd = shopcarMapper.deleteshopca(shopcar);
                }
            }
        }catch (NumberFormatException e){

        }
        AliOrderExample example=new AliOrderExample();
        AliOrderExample.Criteria criteria=example.createCriteria();
        criteria.andOrderIdEqualTo(aliOrder.getOrderId());
        List<AliOrder> list=aliOrderMapper.selectByExample(example);
        return list;
    }
    @Override
    public List<TpRegion2> selectParent(int parentId) {
        List<TpRegion2> list=tpRegion2Mapper.selectparent(parentId);
        return list;
    }

    @Override
    public R selectConsignee() {
        ConsigneeExample example=new ConsigneeExample();
        ConsigneeExample.Criteria criteria=example.createCriteria();
        criteria.andUsIdEqualTo(1);
        List<Consignee> consignee=consigneeMapper.selectByExample(example);
        return R.ok().put("consignee",consignee);
    }

    @Override
    public R updateConsignee(Consignee consignee) {
        int i=consigneeMapper.updateConsig(consignee);
        return i>0?R.ok():R.error("修改失败");
    }

    @Override
    public List<Product> getProByAliNum(Long aliNum) {

        OrderItemsExample orderItemsExample=new OrderItemsExample();
        OrderItemsExample.Criteria criteria1=orderItemsExample.createCriteria();
        criteria1.andProItmEqualTo(aliNum);
        List<OrderItems> orderItems = orderItemsMapper.selectByExample(orderItemsExample);
        List list = new ArrayList();
        if (orderItems!=null) {
            for (OrderItems orderItem : orderItems) {
                Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
                list.add(product);
            }
            return list;
        }
        return null;
    }

}
