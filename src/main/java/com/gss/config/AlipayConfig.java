package com.gss.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092600600592";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnHgHe1LNjCIaL\n" +
            "B9gNxFYEmaG5O6qBnVIzP8E8+ZtAhm0mWoGm7nhfeiwvC7R8zOU3dvMX4tHOE9Ka\n" +
            "64B9NcdOwEfll1TXDVWuBWQY96hboAbQFluUeCWz/7TOmULHKraFDYz0ZH0D5Nvn\n" +
            "bo3sn8O5ckt4YFYeNnF4hrKzay2WjIgpld7qSQmh16xbq9Pu6Vd3qXGSyYUfAWgm\n" +
            "O+i77Ymck7N2Zn/p4QjvuCvWk5SlJ1xyHRSPtKT/R39FtYAvSMhx6sLB7o/5qduc\n" +
            "WfXvdUfRMCo+EzrnQ/JD8sQWl6MO0EaliKmyaCFHe8O+q6muSaO+7zmmW6QIC1m2\n" +
            "ncbi+g85AgMBAAECggEACY6pxLidWbiPGTxlmsPzt2eh3BoRckAxB0VKI9aSjmoh\n" +
            "q+PZLlbNA4jVJguhUtHU+gsPc/pAzWxMyxdUzdRZhaNxeSviajoVAFLGmrbvJe5M\n" +
            "9XGR4zfL0DN7f6U8MG+86HcR7JginipkeSPVcgbhBqYGsuevhSLP8nihl6k1fjqL\n" +
            "iuY8pG05nwVBkZ9JjDt8taThx2SBVNyWzU+CkJsWER2x6yMOTLFsaPvqojLicPnC\n" +
            "wyaX5K9CA6cKQ5r/PL8Y/yxLLJK5WrrwfGDaIVVeVSWuMB4Ot+hHtACS3ncuz+/2\n" +
            "lebk+8lAc61owHdvyu5k19Ht87C8pUjdDwxoEpBWUQKBgQDcB8XySZz2O9FgVbqw\n" +
            "Sro/OHkBQkgJodJR1B3+vtx7kdvedI5d2c+nFVzSs/gGtLxriQwyFRuadfQLc1jI\n" +
            "raOtoEfOi4TSAuebEcep7YelQVA/pOk+LLDHlWeabiEYK32NANbgEP9HQv9oE23C\n" +
            "8Kv6dxvfyD1616aC3vog9a+gnwKBgQDCb9RTuFBo5bEDstOwYOcs7wd/DJfWGiZe\n" +
            "C75MVktW+cuRvXmA4nXZWwR+JchRS6golV7QxPg1O2Nw6K3woBXSiSWpkP1rFMrG\n" +
            "dkMqzK+je6f0CbXzlB7OCyTJAanRtcfCzh8mEQAx7a6Zj1x7wL68QRRBKvrkv3RA\n" +
            "lOAWj40JJwKBgAuMnOdK1eJW4XadCV+XjI8k97UJNZ7Lz65b4/crLYeO8GuM0qw6\n" +
            "cpvikyy8JZi0g+9hEjLOyNQivNydAVwHQUAr2ZkN2brsTBvnowNYZHZYJ04v6c5J\n" +
            "qj3PqdCBV1tdbQmpsZ2xRrUG30EnQ+unll9fQSdN62w3RZY4/ggc3UshAoGBAL4j\n" +
            "TadxHnD17NyJC6n3mRm/xbzgO7sLZ2mgYj+UQ6iSLhgfCaqXylb7QrF5kSKusYq+\n" +
            "RzIw6+hiYyQO5cnhQnXX2BSxzJ/ifUkGbTh1YWylh+CFfoDr4BPtodNO5RpGgUpm\n" +
            "9t7q0R4CKDPSlcYO/nZpzbzMovz+nFhrPZfSWUERAoGAAyZxYXN9Irr/OU+DtBal\n" +
            "G9UrVRZY6e1Lp9Hr1jg4UQb3ru0kJgziMA3JTqfuHeF9boRCDVCoRJgrZmW0UP4Q\n" +
            "/p8VTC70PlNizKC226hRrPecTB8Y7CTgp47Vdunhb+kXw826jg6hF1v9i3gf3r/h\n" +
            "jOiPvuDW2ECi3wlISn5i+A8=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApx4B3tSzYwiGiwfYDcRW\n" +
            "BJmhuTuqgZ1SMz/BPPmbQIZtJlqBpu54X3osLwu0fMzlN3bzF+LRzhPSmuuAfTXH\n" +
            "TsBH5ZdU1w1VrgVkGPeoW6AG0BZblHgls/+0zplCxyq2hQ2M9GR9A+Tb526N7J/D\n" +
            "uXJLeGBWHjZxeIays2stloyIKZXe6kkJodesW6vT7ulXd6lxksmFHwFoJjvou+2J\n" +
            "nJOzdmZ/6eEI77gr1pOUpSdcch0Uj7Sk/0d/RbWAL0jIcerCwe6P+anbnFn173VH\n" +
            "0TAqPhM650PyQ/LEFpejDtBGpYipsmghR3vDvquprkmjvu85plukCAtZtp3G4voP\n" +
            "OQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://s24293p248.qicp.vip/callback.html";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://s24293p248.qicp.vip/return.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

