package com.gss.controller;

import com.gss.dto.CookbookDTO;
import com.gss.entity.Cookbook;
import com.gss.service.SysBookService;
import com.gss.service.SysUserService;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys")
@Api(value = "菜谱控制器",produces = "application/json")
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys")
@Api(value = "菜谱",produces = "application/json")
public class SysBookController {

    @Resource
    private SysBookService sysBookService;
    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "发布",notes = "发布菜谱")
    @RequestMapping(value = "/user/book/addBook",method = RequestMethod.POST)
    public R add(@RequestBody CookbookDTO cookbook){
        return sysBookService.add(cookbook);
    }

    @ApiOperation(value = "收藏菜谱",notes = "收藏菜谱")
    @RequestMapping(value = "/user/bookAddCollect",method = RequestMethod.POST)
    public R addCollect(int bookId){
        return sysBookService.addCollect(bookId);
    }

    @ApiOperation(value = "模糊查询菜谱或食材",notes = "模糊查询菜谱或食材")
    @RequestMapping(value = "/book/fuzzySelectBook",method = RequestMethod.POST)
    public ResultData selectBook(Pager pager, String search){
        return sysBookService.selectBook(pager,search);
    }

    @ApiOperation(value = "菜谱评论",notes = "菜谱评论")
    @RequestMapping(value = "/book/comment",method = RequestMethod.POST)
    public R comment(int bookId,String commentValue){
        return sysBookService.comment(bookId,commentValue);
    }

    @ApiOperation(value = "查询菜谱",notes = "根据菜谱ID查询菜谱详情")
    @RequestMapping(value = "/book/selectById",method = RequestMethod.POST)
    public R selectByBookId(int bookId){
        CookbookDTO cookbookDTO = sysBookService.selectBookById(bookId);
        User user = (User) sysUserService.selectMyHome(cookbookDTO.getUsId()).get("user");
        return R.ok().put("cookbookdto",cookbookDTO).put("user",user);
    }
    //根据菜谱类型获得菜谱简略信息
    @ApiOperation(value = "根据菜谱类型查询",notes = "根据菜谱类型获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByType",method = RequestMethod.GET)
    public ResultData selectByType(int typeId , Pager pager){
        return sysBookService.selectByType(typeId,pager);
    }

    @ApiOperation(value = "查询首页时间菜谱",notes = "根据菜谱类型获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByTimeType",method = RequestMethod.GET)
    public R selectByTimeType(int typeId){
        return sysBookService.selectByTimeType(typeId);
    }
    @ApiOperation(value = "查询首页推荐菜谱",notes = "获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByBest",method = RequestMethod.GET)
    public R selectByBest(){
        return sysBookService.selectByBest();
    }

}
