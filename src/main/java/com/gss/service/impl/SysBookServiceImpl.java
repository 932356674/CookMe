package com.gss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gss.dto.CookbookDTO;
import com.gss.entity.*;
import com.gss.mapper.*;
import com.gss.service.SysBookService;
import com.gss.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import com.gss.entity.Cookbook;
import com.gss.mapper.CookbookMapper;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.RandomUtils;
import com.gss.utils.ResultData;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private BookCommentMapper bookCommentMapper;
    @Resource
    private BooktypeMapper booktypeMapper;

    @Override
    public R add(CookbookDTO cookbook) {
        try{
            cookbook.setUsId(ShiroUtils.getUserId());
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
            User u = userMapper.selectByPrimaryKey(ShiroUtils.getUserId());
            u.setUsBookcount(u.getUsBookcount()+1);
            userMapper.updateByPrimaryKey(u);
            List<Booktype> types = cookbook.getTypes();
            for (Booktype type : types) {
                CookbookType cookbookType = new CookbookType();
                cookbookType.setTypeId(type.getTypeId());
                cookbookType.setBookId(cookbook.getBookId());
                cookbookTypeMapper.insert(cookbookType);
            }
        }catch(Exception e){
            e.printStackTrace();
            return R.error("新增失败！");
        }
        return R.ok("新增成功！");
    }


    @Override
    public R addCollect(int bookId) {
        Collect collect = new Collect();
        collect.setUsId(ShiroUtils.getUserId());
        collect.setBookId(bookId);
        int i = collectMapper.insert(collect);
        if (i>0){
            return R.ok("收藏成功！");
        }else{
            return R.error("收藏失败！");
        }
    }

    @Override
    public ResultData selectBook(Pager pager, String search) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        CookbookExample cookbookExample = null;
        MaterialExample materialExample = null;
        if(StringUtils.isNotEmpty(search)){
            cookbookExample = new CookbookExample();
            CookbookExample.Criteria criteria = cookbookExample.createCriteria();
            criteria.andBookNameLike("%"+search+"%");

            materialExample = new MaterialExample();
            MaterialExample.Criteria criteria1 = materialExample.createCriteria();
            criteria1.andMatNameLike("%"+search+"%");
        }
        List<Cookbook> list = cookbookMapper.selectByExample(cookbookExample);
        List<Material> list1 = materialMapper.selectByExample(materialExample);
        List<Integer> list2=new ArrayList<>();
        for (Cookbook cookbook : list) {
            list2.add(cookbook.getBookId());
        }
        for (Material material : list1) {
            if (!list2.contains(material.getBookId())){
                list.add(cookbookMapper.selectByPrimaryKey(material.getBookId()));
            }
        }
        PageInfo info = new PageInfo(list);
        return new ResultData(info.getTotal(),info.getList());
    }

    @Override
    public R comment(int bookId, String commentValue) {

        BookComment bookComment = new BookComment();
        bookComment.setBookId(bookId);
        bookComment.setCommentTime(new Date());
        bookComment.setCommentValue(commentValue);
        bookComment.setUsId(ShiroUtils.getUserId());
        int i = bookCommentMapper.insert(bookComment);
        if (i>0){
            return R.ok("评论成功！");
        }else{
            return R.error("评论失败！");
        }
    }

    @Override
    public CookbookDTO selectBookById(int bookId) {
        CookbookDTO cookbookDTO = new CookbookDTO();

        Cookbook cookbook = cookbookMapper.selectByPrimaryKey(bookId);

        CookbookTypeExample example = new CookbookTypeExample();
        CookbookTypeExample.Criteria criteria= example.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        List<CookbookType> bt = cookbookTypeMapper.selectByExample(example);

        List<Booktype> booktypes = new ArrayList<Booktype>();
        for (CookbookType cookbookType : bt) {
            int i = cookbookType.getTypeId();
            Booktype booktype = booktypeMapper.selectByPrimaryKey(i);
            booktypes.add(booktype);
        }
        cookbookDTO = (CookbookDTO) cookbook;
        cookbookDTO.setTypes(booktypes);

        StepExample stepExample = new StepExample();
        StepExample.Criteria stepcriteria = stepExample.createCriteria();
        stepcriteria.andBookIdEqualTo(bookId);
        List<Step> stepList = stepMapper.selectByExample(stepExample);

        MaterialExample materialExample = new MaterialExample();
        MaterialExample.Criteria criteria1 = materialExample.createCriteria();
        criteria1.andBookIdEqualTo(bookId);
        List<Material> materials = materialMapper.selectByExample(materialExample);
        cookbookDTO.setMaterial(materials);
        cookbookDTO.setMethod(stepList);

        return cookbookDTO;
    }





    @Override
    public R selectByBest() {
        List<Cookbook> list=cookbookMapper.selectByExample(null);
        Set<Integer> set=RandomUtils.getRondom(list.size(),2);
        List<Cookbook> list1=new ArrayList<>();
        for (Integer integer : set) {
            list1.add(list.get(integer));
        }
        return new R().put("bestType",list1);
    }

    @Override
    public R selectByTimeType(int typeId) {
        List<Cookbook> list=cookbookMapper.selectByType(typeId);
        Set<Integer> set=RandomUtils.getRondom(list.size(),2);
        List<Cookbook> list1=new ArrayList<>();
        for (Integer integer : set) {
            list1.add(list.get(integer));
        }
        return new R().put("timeType",list1);
    }


    @Override
    public ResultData selectByType(int typeId, Pager pager) {
        PageHelper pageHelper=new PageHelper();
        pageHelper.offsetPage(pager.getOffset(),pager.getLimit());

        List<Cookbook> list=cookbookMapper.selectByType(typeId);
        PageInfo pageInfo=new PageInfo(list);
        list=pageInfo.getList();
        return new ResultData(pageInfo.getTotal(),list);
    }
}
