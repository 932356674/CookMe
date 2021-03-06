package com.gss.controller;

import com.gss.dto.ShopCarDto;
import com.gss.entity.Product;
import com.gss.entity.Shopcar;
import com.gss.service.SysShopService;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gss.utils.Pager;
import com.gss.utils.ResultData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

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
 * @Time: 18:42
 */
@RestController
@RequestMapping("/sys")
@CrossOrigin(origins = {"*"})
@Api(value = "购物车商城",produces = "application/json")
public class SysShopController {

    @Resource
    private SysShopService sysShopService;

    @ApiOperation(value = "购物车商品详情",notes = "查询商品")
    @RequestMapping(value = "/user/selectCommodityDetails",method = RequestMethod.GET)
    public Product selectProduct(@RequestParam("productId") Integer productId){
        return sysShopService.selectProduct(productId);
    }
    @ApiOperation(value = "购物车",notes = "查询商品")
    @RequestMapping(value = "/user/shopcar",method = RequestMethod.POST)
    public R selectProduct(@RequestBody ShopCarDto shopCarDto){
        shopCarDto.setUsId(ShiroUtils.getUserId());//ShiroUtils.getUserId()
        Shopcar shopcar3 = sysShopService.selectShopCarByUsProductId(shopCarDto);
        List<ShopCarDto> list = new ArrayList<ShopCarDto>();
        if(shopcar3!=null){
            shopcar3.setCarCount(shopcar3.getCarCount()+shopCarDto.getCarCount());
            int u = sysShopService.updateShopCar(shopcar3);
            if(u<1){
                return R.error("服务器忙，抢刷新页面");
            }
        }else{
            Shopcar shopcar = new Shopcar();
            shopcar.setProductId(shopCarDto.getProductId());
            shopcar.setUsId(shopCarDto.getUsId());//ShiroUtils.getUserId()
            shopcar.setCarCount(shopCarDto.getCarCount());
            System.out.println(shopcar.toString());
            int i = sysShopService.insertShopCar(shopcar);
            if(i<1){
                return R.error("服务器忙，抢刷新页面");
            }
        }
        List<Shopcar> shopcar1 = sysShopService.selectShopcarByUsId(shopCarDto.getUsId());//ShiroUtils.getUserId()
        System.out.println(shopcar1);
        for (int i = 0 ; i < shopcar1.size() ; i++){
            Shopcar shopcar2 = shopcar1.get(i);
            Product product = sysShopService.selectProduct(shopcar2.getProductId());
            ShopCarDto shopCarDto1 = new ShopCarDto();
            shopCarDto1.setPrice(product.getPrice());
            shopCarDto1.setProductName(product.getProductName());
            shopCarDto1.setCarCount(shopcar2.getCarCount());
            shopCarDto1.setCarId(shopcar2.getCarId());
            shopCarDto1.setUsId(shopCarDto.getUsId());//ShiroUtils.getUserId()
            shopCarDto1.setProductId(shopcar2.getProductId());
            shopCarDto1.setIsChecked(shopcar2.getIsChecked());
            list.add(shopCarDto1);
        }
        return R.ok().put("list",list);
    }

    @ApiOperation(value = "我的购物车",notes = "查询我的商品")
    @RequestMapping(value = "/user/myshopcar",method = RequestMethod.POST)
    public List<ShopCarDto> selectMyShopCarProduct(){
        List<ShopCarDto> list = new ArrayList<ShopCarDto>();
        List<Shopcar> shopcar1 = sysShopService.selectShopcarByUsId(ShiroUtils.getUserId());
        System.out.println(shopcar1);
        for (int i = 0 ; i < shopcar1.size() ; i++){
            Shopcar shopcar2 = shopcar1.get(i);
            Product product = sysShopService.selectProduct(shopcar2.getProductId());
            ShopCarDto shopCarDto1 = new ShopCarDto();
            shopCarDto1.setPrice(product.getPrice());
            shopCarDto1.setProductName(product.getProductName());
            shopCarDto1.setCarCount(shopcar2.getCarCount());
            shopCarDto1.setCarId(shopcar2.getCarId());
            shopCarDto1.setIsChecked(shopcar2.getIsChecked());
            shopCarDto1.setUsId(ShiroUtils.getUserId());
            shopCarDto1.setProductId(shopcar2.getProductId());

            list.add(shopCarDto1);
        }
        return list;
    }

    @ApiOperation(value = "删除",notes = "删除我的商品")
    @RequestMapping(value = "/user/shopcardel",method = RequestMethod.POST)
    public R delShopCar(@RequestBody List<Shopcar> shopcars){
       List<Integer> carId = new ArrayList<Integer>();
        for (int i = 0 ; i < shopcars.size() ; i++){
         carId.add(shopcars.get(i).getCarId());
        }
        return sysShopService.delShopCarDel(carId);
    }

    @ApiOperation(value = "多选",notes = "多选我的商品")
    @RequestMapping(value = "/user/multiple",method = RequestMethod.POST)
    public R multipleShopCar(@RequestBody List<Shopcar> shopcar){
      return  sysShopService.multipleShopCar(shopcar);
    }


    @ApiOperation(value = "首页根据商品类型随机查询",notes = "根据商品类型获得商品简略信息")
    @RequestMapping(value = "/shop/selectByType/{sort}",method = RequestMethod.POST)
    public R selectByType(@PathVariable String sort){
        if (sort.equals("0")){
            sort = "蔬菜水果";
        }
        if (sort.equals("1")){
            sort = "家禽肉类";
        }
        if (sort.equals("2")){
            sort = "水产冻品";
        }
        if (sort.equals("3")){
            sort = "豆腐禽蛋";
        }
        if (sort.equals("4")){
            sort = "干货粮油";
        }
        return sysShopService.selectByType(sort);
    }





    @ApiOperation(value = "首页",notes = "菜市场首页")
    @RequestMapping(value = "/product/index",method = RequestMethod.POST)
    public Map shop(){
        List<Product> timeProduct = sysShopService.selectRecommend();
        Map<String,List<Product>> map = new HashMap<>();
        map.put("timeProduct",timeProduct);
        List<Product> list1 = sysShopService.selectProductList("蔬菜水果");
        map.put("sort0",list1);
        List<Product> list2 = sysShopService.selectProductList("家禽肉类");
        map.put("sort1",list2);
        List<Product> list3 = sysShopService.selectProductList("水产冻品");
        map.put("sort2",list3);
        List<Product> list4 = sysShopService.selectProductList("豆腐禽蛋");
        map.put("sort3",list4);
        List<Product> list5 = sysShopService.selectProductList("干货粮油");
        map.put("sort4",list5);

        return map;
    }

    @ApiOperation(value = "查询",notes = "模糊查询")
    @RequestMapping(value = "/product/select",method = RequestMethod.POST)
    public ResultData product(@RequestBody Pager pager,@RequestParam("search") String search){
        return sysShopService.selectByPage(pager, search);
    }


}
