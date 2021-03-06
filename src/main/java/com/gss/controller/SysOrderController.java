package com.gss.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.gss.config.AlipayConfig;
import com.gss.entity.AliOrder;
import com.gss.entity.Consignee;
import com.gss.entity.OrderItems;
import com.gss.entity.TpRegion2;
import com.gss.service.SysOrderService;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/sys")
@Api(value = "支付宝支付" ,produces = "application/json")
@CrossOrigin(origins = {"*"})
@RestController
public class SysOrderController {
    @Resource
    private SysOrderService orderService;

    //@RequestMapping("create.html")
    public String create(Long amount, Model model) {
        if (amount == null || amount < 0) {
            model.addAttribute("message", "金额不对");

        }
        Integer userId = 1;//模拟登录用户Id
        String orderNum = System.currentTimeMillis() + "";
        amount = amount * 100;//转化为分
        AliOrder order = new AliOrder(orderNum,userId,amount,"CookMe订单");
        orderService.addOrder(order);
        model.addAttribute("order",order);
        return "order.jsp";
    }

    public static void main(String[] args) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.app_id,AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo(System.currentTimeMillis()+"");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
    //@RequestMapping("app.html")//创建订单 amount单位是元
    @ResponseBody
    public String app(Long amount) {
        Integer userId = 1;//模拟当前登录的用户
        String orderNum = System.currentTimeMillis() + "";
        amount = amount * 100;//转化成分
        AliOrder order = new AliOrder(orderNum,userId, amount, "测试订单");
        orderService.addOrder(order);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo(order.getOrderNum());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(String.valueOf(amount / 100.0f));//金额，是元
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.notify_url);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println("呃呃呃呃呃"+response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "";
    }
    @ApiOperation(value = "储存收货人信息",notes = "收货人信息")
    @RequestMapping(value = "/order/consignee",method =RequestMethod.POST)
    public R insertConsignee(@RequestBody Consignee consignee){
        consignee.setUsId(ShiroUtils.getUserId());
        return orderService.insertCon(consignee);
    }

    @ApiOperation(value = "新增订单详情并返回",notes = "订单信息")
    @RequestMapping(value = "/order/itemandpro",method = RequestMethod.POST)
    public List<Object> itemandpro(@RequestBody List<OrderItems> list){
        return orderService.findCount(list);
    }

    @ApiOperation(value = "返回订单信息",notes = "订单信息")
    @RequestMapping(value = "/order/aliOrder",method = RequestMethod.POST)
    public List<AliOrder> aliOrder(@RequestBody AliOrder aliOrder){
        return orderService.findtrade(aliOrder);
    }

    @ApiOperation(value = "下拉框地址信息",notes = "收货人信息")
    @RequestMapping(value = "/order/selectParent",method = RequestMethod.GET)
    public List<TpRegion2> selectParent(@RequestParam("parentId") int parentId){
        return orderService.selectParent(parentId);
    }

    @ApiOperation(value = "查找单个收货人信息",notes = "收货人信息")
    @RequestMapping(value = "/order/selectConsignee",method = RequestMethod.POST)
    public R selectconsignee(){
        return orderService.selectConsignee();
    }

    @ApiOperation(value = "修改收货人信息",notes = "收货人信息")
    @RequestMapping(value = "/order/updateConsignee",method = RequestMethod.POST)
    public R updateConsignee(@RequestBody Consignee consignee){
        return orderService.updateConsignee(consignee);
    }
}
