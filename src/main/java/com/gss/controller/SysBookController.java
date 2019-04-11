package com.gss.controller;

import com.gss.entity.Cookbook;
import com.gss.service.SysBookService;
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
    @ApiOperation(value = "查询",notes = "根据菜谱类型获得菜谱简略信息")
    @RequestMapping(value = "/book/selectByType",method = RequestMethod.POST)
    public List<Cookbook> selectByType(int typeId){
        return sysBookService.selectByType(typeId);
    }

}
