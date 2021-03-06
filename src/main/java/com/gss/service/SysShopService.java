package com.gss.service;

import com.gss.dto.ShopCarDto;
import com.gss.entity.Product;
import com.gss.entity.Shopcar;
import com.gss.utils.R;
import com.gss.utils.Pager;
import com.gss.utils.ResultData;

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
 * @Time: 18:35
 */
public interface SysShopService {
    Product selectProduct(int productId);

    Product selectProductShopCar(int productId);

    int insertShopCar(Shopcar shopCar);

    List<Shopcar> selectShopcarByUsId(int usId);

    Shopcar selectShopCarById(int productId);

    int updateShopCar(Shopcar shopcar);

    Shopcar selectShopCarByUsProductId(ShopCarDto shopCarDto);

    R delShopCarDel(List<Integer> CarId);

    R multipleShopCar(List<Shopcar> shopcars);

    //首页获取
    R selectByType(String sort);


    //菜市场首页
    public List<Product> selectProductList(String sort);

    //模糊查询
    public ResultData selectByPage(Pager pager,String search);

    //今日推荐
    public List<Product> selectRecommend();
}
