package com.houhong.thirdpay.core.common;

import com.houhong.thirdpay.bean.TradeStatus;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-10-06 17:31
 **/
public abstract class AbstractPayAdaptor implements PayAdaptor {


    /**
     * 订单状态分发
     *
     * @param status 订单状态
     */
    public void doTradeStatus(TradeStatus status) {

        if (status.isPaySuccess()) {
            doPaySuccess(status.getTradeNo());
            return;
        }

        if (status.isPaying()) {
            doPayIng(status.getTradeNo());
            return;
        }

        if (status.isPayFail()) {
            doPayFail(status.getTradeNo());
            return;
        }

        if (status.isRefundSuccess()) {
            doRefundSuccess(status.getTradeNo());
            return;
        }

        if (status.isRefundFail()) {
            doRefundFail(status.getTradeNo());
            return;
        }

    }
}