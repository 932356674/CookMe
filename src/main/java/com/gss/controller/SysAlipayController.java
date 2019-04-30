package com.gss.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gss.config.AlipayConfig;
import com.gss.entity.AliOrder;
import com.gss.entity.Product;
import com.gss.service.SysOrderService;
import com.gss.utils.Constants;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

@Api(value = "支付宝支付" ,produces = "application/json")
@RestController
public class SysAlipayController {
    @Autowired
    private SysOrderService orderService;

    @Resource
    private SysOrderService sysOrderService;

    @ApiOperation(value ="调用支付宝支付接口",notes = "调用支付宝支付接口")
    @RequestMapping(value = "/sys/alipay/pay",method = RequestMethod.POST)
    public R order() throws Exception {

        //String orderNum = "1555636552404";
        String orderNum = (String)ShiroUtils.getAttribute("orderNum");
//        System.out.println(ShiroUtils.getAttribute("orderNum"));
//        System.out.println(orderNum+"-------------");
        if (orderNum != null) {
            AliOrder order = orderService.getOrderByOrderNum(orderNum);
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            // 订单名称，必填
            // 付款金额，必填
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


            //商户订单号，商户网站订单系统中唯一订单号，必填
            // ("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            String out_trade_no = orderNum;
            //付款金额，必填
//	String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
            Long amount = order.getAmount();// 100F;
            String total_amount = amount.toString();
            //订单名称，必填
            //String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
            Long aliItem = order.getAliItem();


            List<Product> proByAliNum = sysOrderService.getProByAliNum(aliItem);
            StringBuffer subject = new StringBuffer("");
            if (proByAliNum!=null){
                for (Product product : proByAliNum) {
                    subject.append(product.getProductName());
                    subject.append("、");
                }
                System.out.println("+++++"+subject.toString());
            }
            System.out.println("单号"+orderNum+"---"+"金额"+total_amount+"---"+"阿里Item"+aliItem);

            //商品描述，可空
            //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    //+ "\"body\":\""+ body +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
            //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
            //		+ "\"total_amount\":\""+ total_amount +"\","
            //		+ "\"subject\":\""+ subject +"\","
            //		+ "\"body\":\""+ body +"\","
            //		+ "\"timeout_express\":\"10m\","
            //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            System.out.println(result);
            //输出
           return R.ok().put("result",result);
        }
        return R.error("获取连接失败！");
    }

    @ApiOperation(value ="支付宝",notes = "获取支付宝GET过来反馈信息")
    @RequestMapping(value = "return.html",method = RequestMethod.GET)
    public R returnPage(HttpServletRequest request) throws Exception {
       /* *
 * 功能：支付宝服务器同步通知页面
 * 日期：2017-03-30
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


 *************************页面功能说明*************************
 * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
 */

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //该页面可做页面美工编辑
            //Order order = orderService.getOrderByOrderNum(orderNum);
            //判断交易状态，判断支付金额
            System.out.println("交易状态：" + params.get("trade_status"));
            System.out.println("支付金额：" + params.get("total_amount"));
            return R.ok("交易成功");
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
        } else {
            //该页面可做页面美工编辑
            return R.error("交易失败");
        }
    }

    @ApiOperation(value ="支付宝",notes = "获取支付宝POST过来反馈信息")
    @RequestMapping(value = "callback.html",method = RequestMethod.POST)
    public R callback(HttpServletRequest request) throws Exception {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
    1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            System.out.println("哈哈哈1");
            AliOrder order = orderService.getOrderByOrderNum(out_trade_no);
            if (order == null) {
                System.out.println("哈哈哈2");

                return R.error("验证失败");
            }
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                System.out.println("哈哈哈3");


                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                String sellerId = params.get("seller_id");
                if (!sellerId.equals("2088102177349202")) {//如果商户号一致，代表是我收的钱

                    System.out.println("哈哈哈4");

                    return R.error("商户号不一致");

                }
                Float totalFee = Float.valueOf(params.get("total_amount")) * 100;//交易金额，单位是元,需要转化成分
                int amount = totalFee.intValue();


                if (order.getStatus() == Constants.ORDER_STATUS_SUCCESS) {//如果是已经处理过的订单,则不处理
                    System.out.println("哈哈哈5");

                    return R.ok("已经处理过");
                }
                if (order.getAmount().equals(amount)) {//金额一致
                    System.out.println("哈哈哈");
                    order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    order.setCreateDate(new Date());
                    order.setTradeNum(trade_no);
                    //order.setResult(trade_status);
                    orderService.updateOrder(order);
                    System.out.println("哈哈哈6");
                    //加话费、发游戏道具。。。。通知卖家。。。。
                }
                System.out.println("成功");
                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else {
                order.setStatus(Constants.ORDER_STATUS_FAIL);
                order.setCreateDate(new Date());
                order.setTradeNum(trade_no);
                //order.setResult(trade_status);
                orderService.updateOrder(order);
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            return R.ok("交易成功");    //请不要修改或删除
            //////////////////////////////////////////////////////////////////////////////////////////
        } else {//验证失败
            return R.error("验证失败");
        }

        //——请在这里编写您的程序（以上代码仅作参考）——


    }

}
