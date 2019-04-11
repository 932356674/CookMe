
package com.gss.controller;

import com.gss.dto.UserAttentionDto;
import com.gss.entity.Attention;
import com.gss.service.SysAttentionService;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
 * @Time: 11:47
 */
@RestController
@RequestMapping("/sys")
@Api(value = "我的关注粉丝",produces = "application/json")
public class SysAttentionConntroller {

    @Resource
    private SysAttentionService attentionService;

    @ApiOperation(value = "关注",notes = "查询我关注的人")
    @RequestMapping(value = "/user/selectMyAttention",method = RequestMethod.POST)
    public List<UserAttentionDto> selectMyAttention(){
        int userId = ShiroUtils.getUserId();
        System.out.println(userId);
        return attentionService.selectAttentionFans(userId);
    }
    @ApiOperation(value = "粉丝",notes = "查询我的粉丝")
    @RequestMapping(value = "/user/fans",method = RequestMethod.POST)
    public List<UserAttentionDto> selectMyFans(){
        int userId = ShiroUtils.getUserId();
        return attentionService.selectMyFans(userId);
    }

    @ApiOperation(value = "他关注",notes = "查询他关注的人")
    @RequestMapping(value = "/user/selectOtherAttention",method = RequestMethod.POST)
    public List<UserAttentionDto> selectOtherAttention(@RequestBody Integer usId){
        return attentionService.selectAttentionFans(usId);
    }

    @ApiOperation(value = "他的粉丝",notes = "查询他的粉丝")
    @RequestMapping(value = "/user/selectOtherFans",method = RequestMethod.POST)
    public List<UserAttentionDto> selectOtherFans(@RequestBody Integer usId){
        return attentionService.selectMyFans(usId);
    }

    @ApiOperation(value = "关注状态",notes = "改变关注状态")
    @RequestMapping(value = "/user/attention",method = RequestMethod.POST)
    public R updateAttentionStatus(@RequestBody Attention attention){
        //判断关注状态是否为已关注
        if(attention.getStatus()==0){
            List<UserAttentionDto> list = attentionService.selectAttentionFans(attention.getAttentionId());
            for (UserAttentionDto userAttentionDto : list) {
                System.out.println(userAttentionDto.getId());
                if(userAttentionDto.getAttentionId()==attention.getFansId()){
                    userAttentionDto.setStatus(0);
                    Attention attention1 = new Attention(userAttentionDto.getId(),userAttentionDto.getFansId(),userAttentionDto.getAttentionId(),userAttentionDto.getStatus());
                    int i = attentionService.updateAttentionStauts(attention1);
                    if(i>0){
                      return attentionService.insertAttentionStauts(attention);
                    }else {
                        return R.error("服务器忙，请刷新页面");
                    }
                }
            }
            attention.setStatus(1);
            return attentionService.insertAttentionStauts(attention);
        }
        else {
            List<UserAttentionDto> list = attentionService.selectAttentionFans(attention.getAttentionId());
            for (UserAttentionDto userAttentionDto : list) {
                System.out.println(userAttentionDto.getId());
                if(userAttentionDto.getAttentionId()==attention.getFansId()){
                    userAttentionDto.setStatus(1);
                    Attention attention1 = new Attention(userAttentionDto.getId(),userAttentionDto.getFansId(),userAttentionDto.getAttentionId(),userAttentionDto.getStatus());
                    int i = attentionService.updateAttentionStauts(attention1);
                    if(i>0){

                        return attentionService.deleteAttention(attention.getId());
                    }else {
                        return R.error("服务器忙，请刷新页面");
                    }
                }
            }
            return attentionService.deleteAttention(attention.getId());
        }

    }
}