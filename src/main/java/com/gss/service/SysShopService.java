package com.gss.service;
import com.gss.entity.Product;
import com.gss.entity.Shopcar;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.ResultData;
import java.util.List;


public interface SysShopService {
    Product selectProduct(int productId);

    Product selectProductShopCar(int productId);

    R insertShopCar(Shopcar shopCar);

    List<Shopcar> selectShopcar();


    //菜市场首页
    public List<Product> selectProductList(String sort);

    //模糊查询
    public ResultData selectByPage(Pager pager, String search);

    //今日推荐
    public List<Product> selectRecommend();
}
