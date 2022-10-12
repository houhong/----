package com.houhong.thirdpay.bean.common;

import com.houhong.thirdpay.bean.TradeStatus;
import com.houhong.thirdpay.exception.RefundException;

/**
*
*  支付定义
*
**/
public interface Pay<T extends Trade>{

    /**
     * 支付
     * @param trade 订单
     * @return 订单凭证
     */
    TradeToken<?> pay(T trade);

    /**
     * 退款
     * @param trade 订单
     * @throws RefundException 退款失败抛出
     */
    void refund(T trade) throws RefundException;

    /**
     * 订单状态查询
     * @param trade 订单
     * @return 订单状态
     */
    TradeStatus status(T trade);
}
