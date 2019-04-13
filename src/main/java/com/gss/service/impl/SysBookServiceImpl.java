package com.gss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gss.entity.Cookbook;
import com.gss.mapper.CookbookMapper;
import com.gss.service.SysBookService;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.RandomUtils;
import com.gss.utils.ResultData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SysBookServiceImpl implements SysBookService {
    @Override
    public R selectByBest() {
        List<Cookbook> list=cookbookMapper.selectByExample(null);
        Set<Integer> set=RandomUtils.getRondom(list.size(),2);
        List<Cookbook> list1=new ArrayList<>();
        for (Integer integer : set) {
            list1.add(list.get(integer));
        }
        return new R().put("bestType",list1);
    }

    @Override
    public R selectByTimeType(int typeId) {
        List<Cookbook> list=cookbookMapper.selectByType(typeId);
        Set<Integer> set=RandomUtils.getRondom(list.size(),2);
        List<Cookbook> list1=new ArrayList<>();
        for (Integer integer : set) {
            list1.add(list.get(integer));
        }
        return new R().put("timeType",list1);
    }

    @Resource
    private CookbookMapper cookbookMapper;
    @Override
    public ResultData selectByType(int typeId, Pager pager) {
        PageHelper pageHelper=new PageHelper();
        pageHelper.offsetPage(pager.getOffset(),pager.getLimit());

        List<Cookbook> list=cookbookMapper.selectByType(typeId);
        PageInfo pageInfo=new PageInfo(list);
        list=pageInfo.getList();
        return new ResultData(pageInfo.getTotal(),list);
    }
}
