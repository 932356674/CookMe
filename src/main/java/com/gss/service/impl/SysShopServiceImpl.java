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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gss.entity.Product;
import com.gss.entity.ProductExample;
import com.gss.mapper.ProductMapper;
import com.gss.service.SysShopService;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.ResultData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service(value = "sysShopServiceImpl")
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






    @Override
    public List<Product> selectProductList(String sort) {
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andSortEqualTo(sort);
        List<Product> list = productMapper.selectByExample(example);

        return list;
    }




    @Override
    public ResultData selectByPage(Pager pager, String search) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        if (search != null && !"".equals(search)){
            criteria.andProductNameLike("%"+search+"%");
        }
        List<Product> list = productMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData data = new ResultData(info.getTotal(),info.getList());

        return data;
    }

    @Override
    public List<Product> selectRecommend() {
        List<Product> recommend = productMapper.selectByRandom();

        return recommend;
    }
}

