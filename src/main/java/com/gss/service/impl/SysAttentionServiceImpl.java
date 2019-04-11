package com.gss.service.impl;

import com.gss.dto.UserAttentionDto;
import com.gss.entity.Attention;
import com.gss.entity.AttentionExample;
import com.gss.entity.User;
import com.gss.mapper.AttentionMapper;
import com.gss.mapper.UserMapper;
import com.gss.service.SysAttentionService;
import com.gss.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * @Date: 2019/4/10
 * @Time: 8:46
 */
@Service
public class SysAttentionServiceImpl implements SysAttentionService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AttentionMapper attentionMapper;

    @Override
    public List<UserAttentionDto> selectAttentionFans(Integer usId) {
        List<UserAttentionDto> user = userMapper.selectAttentionFans(usId);
        return user;
    }

    @Override
    public List<UserAttentionDto> selectMyFans(Integer usId) {
        List<UserAttentionDto> user = userMapper.selectMyFans(usId);
        return user;
    }

    @Override
    public R insertAttentionStauts(Attention attention) {
        int a = attentionMapper.insertSelective(attention);
        System.out.println(attention.getFansId());

        int c = userMapper.selectMyFansCounts(attention.getAttentionId());
        User user = userMapper.selectByPrimaryKey(attention.getAttentionId());
        if (a>0){
            c+=1;
            user.setUsFanscount(c);
            int i = userMapper.updateByPrimaryKeySelective(user);
            System.out.println(user.toString());
            if(i>0){
                return R.ok();
            }
            else {
                return R.error("服务器忙，请刷新页面");
            }
        }else {
            return R.error("服务器忙，请刷新页面");
        }

    }

    @Override
    public int updateAttentionStauts(Attention attention) {
        int a = attentionMapper.updateByPrimaryKeySelective(attention);
        return a;
    }

    @Override
    public R deleteAttention(Integer attentionId) {
        int a = attentionMapper.deleteByPrimaryKey(attentionId);
        if (a>0){
            return R.ok();
        }else {
            return R.error("服务器忙，请刷新页面");
        }
    }

}
