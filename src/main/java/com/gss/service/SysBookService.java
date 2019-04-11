package com.gss.service;

import com.gss.entity.Cookbook;

import java.util.List;

public interface SysBookService {
    List<Cookbook> selectByType(int typeId);
}
