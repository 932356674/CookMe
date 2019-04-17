package com.gss.service.impl;

import com.gss.entity.Product;
import com.gss.mapper.ProductMapper;
import com.gss.service.SysShopService;
import com.gss.utils.R;
import com.gss.utils.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class SysShopServiceImpl implements SysShopService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public R selectByType(String sort) {
        List<Product> list=productMapper.selectByType(sort);
        Set<Integer> set=RandomUtils.getRondom(list.size(),2);
        List<Product> list1=new ArrayList<>();
        for (Integer integer : set) {
            list1.add(list.get(integer));
        }
        return new R().put("bestProduct",list1);
    }
}
