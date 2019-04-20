package com.gss.service;

import com.gss.entity.Product;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.ResultData;

public interface SysShopService {
    R selectByType(String sort);
import java.util.List;

public interface SysShopService {
    //菜市场首页
    public List<Product> selectProductList(String sort);

    //模糊查询
    public ResultData selectByPage(Pager pager,String search);

    //今日推荐
    public List<Product> selectRecommend();
}
