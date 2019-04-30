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
import com.gss.entity.ProductExample;
import com.gss.utils.Pager;
import com.gss.utils.ResultData;
import com.gss.dto.ShopCarDto;
import com.gss.entity.Shopcar;
import com.gss.mapper.ShopcarMapper;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖镇楼                  BUG辟易
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 *
 * @Author: M.J
 * @Date: 2019/4/14
 * @Time: 18:39
 */
@Service
public class SysShopServiceImpl implements SysShopService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private ShopcarMapper shopcarMapper;


    @Override
    public Product selectProduct(int productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Override
    public Product selectProductShopCar(int productId) {

        return productMapper.selectProduct(productId);
    }


    @Override
    public int insertShopCar(Shopcar shopCar) {
        System.out.println(shopCar);
        return shopcarMapper.insert(shopCar);

    }

    @Override
    public List<Shopcar> selectShopcarByUsId(int usId) {

        return shopcarMapper.selectShorcarByUsId(usId);
    }

    @Override
    public Shopcar selectShopCarById(int productId) {
        return  shopcarMapper.selectByPrimaryKey(productId);
    }

    @Override
    public int updateShopCar(Shopcar shopcar) {
        return shopcarMapper.updateByPrimaryKeySelective(shopcar);
    }

    @Override
    public Shopcar selectShopCarByUsProductId(ShopCarDto shopCarDto) {
        return  shopcarMapper.selectShorCarByUsProductId(shopCarDto);
    }

    @Override
    public R delShopCarDel(List<Integer> carId) {
        int a = 0;
        for (int i = 0 ; i < carId.size() ; i++){
             a = shopcarMapper.deleteByPrimaryKey(carId.get(i));
        }
        if(a>0){
            return R.ok();
        }else {
            return R.error("系统繁忙，请刷新页面");
        }
    }

    @Override
    public R multipleShopCar(List<Shopcar> shopcars) {
        int q = 0 ;
        for (int i = 0 ; i < shopcars.size() ; i++){
            System.out.println(shopcars.get(i));
            q = shopcarMapper.updateShopCarMultiple(shopcars.get(i));
        }
        if(q>0){
            return R.ok();
        }else{
            return R.error("服务器忙请刷新页面");
        }
    }

    @Override
    public R selectByType(String sort) {
        List<Product> list=productMapper.selectByType(sort);
        Set<Integer> set=RandomUtils.getRondom(list.size(),6);
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

