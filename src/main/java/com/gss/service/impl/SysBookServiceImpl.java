package com.gss.service.impl;

import com.gss.entity.Cookbook;
import com.gss.mapper.CookbookMapper;
import com.gss.service.SysBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SysBookServiceImpl implements SysBookService {
    @Resource
    private CookbookMapper cookbookMapper;
    @Override
    public List<Cookbook> selectByType(int typeId) {

        return cookbookMapper.selectByType(typeId);
    }
}
