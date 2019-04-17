package com.gss.controller;

import com.gss.dto.CookbookDTO;
import com.gss.entity.Cookbook;
import com.gss.entity.Material;
import com.gss.entity.Step;
import com.gss.service.SysBookService;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.ResultData;
import com.gss.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys")
@Api(value = "菜谱",produces = "application/json")
public class SysBookController {

    @Resource
    private SysBookService sysBookService;

    @ApiOperation(value = "发布",notes = "发布菜谱")
    @RequestMapping(value = "/user/book/addBook",method = RequestMethod.POST)
    public R add(@RequestBody CookbookDTO cookbook){
        return sysBookService.add(cookbook);
    }

    @ApiOperation(value = "收藏菜谱1",notes = "收藏菜谱")
    @RequestMapping(value = "/user/bookAddCollect",method = RequestMethod.POST)
    public R addCollect(int bookId){
        return sysBookService.addCollect(bookId);
    }

    @ApiOperation(value = "模糊查询菜谱或食材1",notes = "模糊查询菜谱或食材")
    @RequestMapping(value = "/book/fuzzySelectBook",method = RequestMethod.POST)
    public ResultData selectBook(Pager pager, String search){
        return sysBookService.selectBook(pager,search);
    }

    @ApiOperation(value = "菜谱评论1",notes = "菜谱评论")
    @RequestMapping(value = "/book/comment",method = RequestMethod.POST)
    public R comment(int bookId,String commentValue){
        return sysBookService.comment(bookId,commentValue);
    }

    @ApiOperation(value = "查询菜谱",notes = "根据菜谱ID查询菜谱详情")
    @RequestMapping(value = "/book/comment",method = RequestMethod.POST)
    public CookbookDTO selectByBookId(int bookId){return sysBookService.selectBookById(bookId);}

}
