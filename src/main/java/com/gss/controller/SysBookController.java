package com.gss.controller;

import com.gss.entity.Cookbook;
import com.gss.service.SysBookService;
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
public class SysBookController {

    @Resource
    private SysBookService sysBookService;
    //根据菜谱类型获得菜谱简略信息
    @ApiOperation(value = "根据菜谱类型查询",notes = "根据菜谱类型获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByType",method = RequestMethod.POST)
    public ResultData selectByType(int typeId , Pager pager){
        return sysBookService.selectByType(typeId,pager);
    }

    @ApiOperation(value = "查询首页时间菜谱",notes = "根据菜谱类型获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByTimeType",method = RequestMethod.POST)
    public R selectByTimeType(int typeId){
        return sysBookService.selectByTimeType(typeId);
    }
    @ApiOperation(value = "查询首页推荐菜谱",notes = "获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByBest",method = RequestMethod.POST)
    public R selectByBest(){
        return sysBookService.selectByBest();
    }

}
