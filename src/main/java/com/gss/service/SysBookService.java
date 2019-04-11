package com.gss.service;

import com.gss.utils.Pager;
import com.gss.utils.ResultData;

import java.util.List;

public interface SysBookService {
    ResultData selectByType(int typeId, Pager pager);
}
