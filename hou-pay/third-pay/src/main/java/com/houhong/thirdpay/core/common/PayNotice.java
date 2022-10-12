package com.houhong.thirdpay.core.common;

import com.houhong.thirdpay.bean.TradeStatus;
import com.houhong.thirdpay.bean.common.NoticeInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
*
*  支付回调
**/
public interface PayNotice<T extends NoticeInfo> {


    /**
     * 接收回调参数
     * @param request
     * @return
     */
    Map<String, String> receiveParams(HttpServletRequest request);

    /**
     * 回调处理
     * @param params 回调参数集合。<br>为了方便抽象，符合HTTP协议标准，这里将所有参数都看成字符串类型<br>参数具体类型在业务逻辑中具体转换
     * @param info 回调参数实体封装
     * @return 订单状态，失败返回null，失败的原因可能是签名验证失败等等
     */
    TradeStatus execute(Map<String, String> params, T info);

    /**
     * 发送响应
     * @param response
     */
    void sendResponse(HttpServletResponse response);
}
