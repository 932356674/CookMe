package com.gss.service.impl;

import com.gss.dto.UserAttentionDto;
import com.gss.entity.*;
import com.gss.mapper.RegistMapper;
import com.gss.mapper.UserMapper;
import com.gss.service.SysUserService;
import com.gss.utils.*;
import com.gss.entity.*;
import com.gss.mapper.CollectMapper;
import com.gss.mapper.CookbookMapper;
import com.gss.mapper.UserMapper;
import com.gss.service.SysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CookbookMapper cookbookMapper;
    @Resource
    private CollectMapper collectMapper;

    @Resource
    private RegistMapper registMapper;

    @Override
    public R selectMobile(Long mobile) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsMobileEqualTo(mobile);

        List<User> users = userMapper.selectByExample(example);

        System.out.println(users);

        if(users.isEmpty()){
            return R.ok("手机号可用");
        }else {
            return R.error("该手机号已注册");
        }
    }

    @Override
    public R getCode(Long mobile) {

        RegistExample registExample = new RegistExample();

        RegistExample.Criteria criteria = registExample.createCriteria();

        criteria.andPhoneEqualTo(mobile);
        //查询数据库中是否是第一次获取验证码
        List<Regist> regist1 = registMapper.selectByExample(registExample);
        //向手机发送验证码并获取
        String code = GetMessageCode.getCode(mobile + "");
        Regist regist2 = new Regist();
        regist2.setPhone(mobile);
        regist2.setCode(Integer.parseInt(code));
        regist2.setData(new Date(System.currentTimeMillis()));
        //如果是第一次获取验证码
        if(regist1.isEmpty()){
            int i = registMapper.insert(regist2);
            if(i>0){
                return R.ok("验证码成功");
            }else {
                return R.error("验证码获取失败");
            }
         //如果不是第一次获取验证码
        }else {
            regist2.setRegistId(regist1.get(0).getRegistId());
            int i = registMapper.updateByPrimaryKeySelective(regist2);

            if(i>0){
                return R.ok("验证码成功。");
            }else {
                return R.error("验证码获取失败");
            }

        }

    }

    @Override
    public R register(User user,int code) {

        RegistExample registExample = new RegistExample();

        RegistExample.Criteria criteria = registExample.createCriteria();

        criteria.andPhoneEqualTo(user.getUsMobile());

        List<Regist> regists = registMapper.selectByExample(registExample);

        //验证用户输入的验证码是否正确
        if(!regists.isEmpty()&&regists.get(0).getCode().equals(code)){

            //第一次注册用户名随机生成
            user.setUsName(System.currentTimeMillis()+"");

            user.setUsCreatedate(new Date(System.currentTimeMillis()));

            String password = user.getUsPassword();
            //Md5加密
            Md5Hash md5Hash = new Md5Hash(password,user.getUsMobile()+"",1024);
            user.setUsPassword(md5Hash.toString());
            int insert = userMapper.insert(user);

            if(insert>0){
                return R.ok("注册成功");
            }else {
                return R.error("注册失败");
            }
        }else {
            return R.error("注册失败!!");
        }

    }

    @Override
    public R verifyCode(Regist regist) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsMobileEqualTo(regist.getPhone());

        List<User> users = userMapper.selectByExample(example);

        if (!users.isEmpty()){

            RegistExample registExample = new RegistExample();

            RegistExample.Criteria criteria1 = registExample.createCriteria();

            criteria1.andPhoneEqualTo(regist.getPhone());

            List<Regist> regist1 = registMapper.selectByExample(registExample);

            if(regist.getCode().equals(regist1.get(0).getCode())){
                return R.ok("认证成功");
            }else{
                return R.error("验证码错误");
            }
        }else {
            return R.error("用户不存在");
        }
    }

    @Override
    public R resetPwd(User user) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsMobileEqualTo(user.getUsMobile());
        List<User> users = userMapper.selectByExample(example);
        Md5Hash md5Hash = new Md5Hash(user.getUsPassword(),user.getUsMobile()+"",1024);
        users.get(0).setUsPassword(md5Hash.toString());
        int i = userMapper.updateByPrimaryKeySelective(users.get(0));
        if(i>0){
            return R.ok("重置成功");
        }
        return R.error("重置失败");
    }


    @Override
    public User login(long phone) {
        UserExample example=new UserExample();
        UserExample.Criteria criteria=example.createCriteria();

        criteria.andUsMobileEqualTo(phone);
        List<User> list=userMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public  List<User> findMobile(long phone) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsMobileEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public R mobileLogin(Regist regist) {

        RegistExample registExample=new RegistExample();
        RegistExample.Criteria criteria=registExample.createCriteria();
        criteria.andPhoneEqualTo(regist.getPhone());
        List<Regist> list=registMapper.selectByExample(registExample);
        if(list.size()>0&&list.get(0).getCode().equals(regist.getCode())){
            return R.ok();
        }
        return R.error("登录失败");
    }



    @Override
    public R selectMyHome(Integer usId) {
        User user = userMapper.selectByPrimaryKey(usId);
        //分享的菜谱
        List<Cookbook> list = cookbookMapper.selectCookbookByUsId(usId);

        //收藏的菜谱
        List<Collect> list1 = collectMapper.selectCookbookByUsId(usId);

        return R.ok().put("user",user).put("cookbook1",list).put("cookbook2",list1);
    }

    @Override
    public R changeInfoById(User user) {
        user.setUsId(ShiroUtils.getUserId());
        user.setUsCreatedate(new Date(System.currentTimeMillis()));
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i>0){
            return R.ok();
        }
        return R.error("完善失败");
    }

    @Override
    public R updatePassword(String newPassword,String oldPassword) {

        int usId = ShiroUtils.getUserId();
        User user = userMapper.selectByPrimaryKey(usId);
        Md5Hash oldPasswords = new Md5Hash(oldPassword,user.getUsMobile()+"",1024);
        oldPassword = oldPasswords.toString();
        Md5Hash newPasswords = new Md5Hash(newPassword,user.getUsMobile()+"",1024);
        newPassword = newPasswords.toString();
        System.out.println(oldPassword+"旧密码");
        System.out.println(newPassword+"新密码");
        if (oldPassword.equals(user.getUsPassword())) {
            user.setUsPassword(newPassword);
            int i = userMapper.updateByPrimaryKey(user);
            if (i > 0) {
                return R.ok();
            }
        }
        return R.error("修改失败");
    }



    @Override
    public R selectBest() {
        List<User> list=userMapper.selectByExample(null);
        Set<Integer> set=RandomUtils.getRondom(list.size(),6);
        List<User> list1=new ArrayList<>();
        for (Integer integer : set) {
            list1.add(list.get(integer));
        }
        return new R().put("bestUser",list1);
    }

    @Override
    public Regist findPhone(long phone) {
        RegistExample example=new RegistExample();
        RegistExample.Criteria criteria=example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<Regist> list = registMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public R updateHead(MultipartFile file) {
        int usId = 1;
        String sb = null;
        try{
            /*byte[] b = file.getBytes();
            String filename = file.getOriginalFilename();

            String suffix = filename.substring(filename.lastIndexOf(".")+1);

            FastDFSClient client = new FastDFSClient("client.conf");

            String paths[] = client.uploadFile(b,suffix);
            StringBuffer sb = new StringBuffer();
            sb.append(paths[0]+File.separator+paths[1]);*/
            sb = Images.getImages(file);

            int i =userMapper.updateHead(sb,usId);
            if (i>0) {
                return R.ok().put("lujin",sb);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return R.error("上传失败").put("lujin",sb);
    }

    @Override
    public R selectHeHome(Integer usId,Integer showId) {
        User user = userMapper.selectByPrimaryKey(usId);
        //分享的菜谱
        List<Cookbook> list = cookbookMapper.selectCookbookByUsId(usId);

        //收藏的菜谱
        List<Collect> list1 = collectMapper.selectCookbookByUsId(usId);

        List<UserAttentionDto> list2 = userMapper.selectAttentionFans(usId);
        Attention attention = new Attention();
        for(int i = 0 ; i < list2.size() ; i++){
            if(list2.get(i).getFansId()==showId||list2.get(i).getAttentionId()==showId){
                attention.setStatus(list2.get(i).getStatus());
                attention.setId(list2.get(i).getId());
                return R.ok().put("user",user).put("cookbook1",list).put("cookbook2",list1).put("attention",attention);
            }
        }
        return R.ok().put("user",user).put("cookbook1",list).put("cookbook2",list1).put("attention",attention);
    }

}
