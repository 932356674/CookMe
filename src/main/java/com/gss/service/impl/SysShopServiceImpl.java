package com.gss.service.impl;

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

