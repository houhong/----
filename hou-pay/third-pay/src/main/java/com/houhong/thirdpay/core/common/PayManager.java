package com.houhong.thirdpay.core.common;


import com.houhong.thirdpay.bean.TradeStatus;
import com.houhong.thirdpay.bean.common.Trade;
import com.houhong.thirdpay.bean.common.TradeToken;
import com.houhong.thirdpay.exception.RefundException;

/**
*
*  支付统一调度器定义
*
**/
public interface PayManager {

    /**
     * 统一移动支付
     * @param trade 订单
     * @return 订单凭证
     */
    <T> TradeToken<T> mobilePay(Trade trade);

    /**
     * 统一电脑网页支付
     * @param trade 订单
     * @return 订单凭证
     */
    <T> TradeToken<T> webPcPay(Trade trade);

    /**
     * 统一手机网页支付
     * @param trade 订单
     * @return 订单凭证
     */
    <T> TradeToken<T> webMobilePay(Trade trade);

    /**
     * 统一扫码支付
     * @param trade 订单
     * @return 订单凭证
     */
    <T> TradeToken<T> qrcodePay(Trade trade);

    /**
     * 统一JS-SDK支付
     * @param trade 订单
     * @return 订单凭证
     */
    <T> TradeToken<T> jsSdkPay(Trade trade);

    /**
     * 统一退款
     * @param trade 订单
     * @return 退款信息
     */
    void refund(Trade trade) throws RefundException;

    /**
     * 统一状态查询
     * @param trade 订单
     * @return 订单状态
     */
    TradeStatus status(Trade trade);
}
