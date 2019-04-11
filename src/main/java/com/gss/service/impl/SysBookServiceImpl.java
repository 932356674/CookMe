package com.gss.service.impl;

import com.gss.dto.CookbookDTO;
import com.gss.entity.*;
import com.gss.mapper.*;
import com.gss.service.SysBookService;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
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
 * @Description:
 * @Company:
 * @Author: jzx
 * @Date: 2019/4/11 0011
 * @Time: 下午 14:21
 */
@Service
public class SysBookServiceImpl implements SysBookService {

    @Resource
    private CookbookMapper cookbookMapper;
    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private StepMapper stepMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CookbookTypeMapper cookbookTypeMapper;

    @Override
    public R add(CookbookDTO cookbook) {
        try{
            //ShiroUtils.getUserId()
            cookbook.setUsId(1);
            cookbook.setBookTime(new Date());
            int i = cookbookMapper.insert(cookbook);
            List<Material> materials = cookbook.getMaterial();
            for (Material material : materials) {
                materialMapper.insert(material);
            }
            List<Step> steps = cookbook.getMethod();
            for (Step step : steps) {
                stepMapper.insert(step);
            }
            User u = userMapper.selectByPrimaryKey(1);
            u.setUsBookcount(u.getUsBookcount()+1);
            userMapper.updateByPrimaryKey(u);
            List<Integer> types = cookbook.getTypes();
            for (Integer type : types) {
                CookbookType cookbookType = new CookbookType();
                cookbookType.setTypeId(type);
                cookbookType.setBookId(cookbook.getBookId());
                cookbookTypeMapper.insert(cookbookType);
            }
        }catch(Exception e){
            e.printStackTrace();
            return R.error("新增失败！");
        }
        return R.ok("新增成功！");
    }
}
