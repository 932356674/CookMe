package com.gss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gss.entity.Cookbook;
import com.gss.mapper.CookbookMapper;
import com.gss.service.SysBookService;
import com.gss.utils.Pager;
import com.gss.utils.ResultData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SysBookServiceImpl implements SysBookService {
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
