package com.gss.controller;

import com.gss.service.SysBookService;
import com.gss.service.SysShopService;
import com.gss.utils.Pager;
import com.gss.utils.R;
import com.gss.utils.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys")
@Api(value = "商品控制器",produces = "application/json")
public class SysShopController {

    @Resource
    private SysShopService sysShopService;
    //根据菜谱类型获得菜谱简略信息
    @ApiOperation(value = "根据商品类型查询",notes = "根据商品类型获得商品简略信息")
    @RequestMapping(value = "/shop/selectByType",method = RequestMethod.GET)
    public R selectByType(String sort){
        return sysShopService.selectByType(sort);
    }



}
