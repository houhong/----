package com.houhong.thirdpay.core.common;


/**
 * 支付适配器定义
 * <p>此适配器用来适配(解耦)涉及数据访问的业务逻辑</p>
 * <p>不同的支付业务可以有不同的适配器实现</p>
 */
public interface PayAdaptor {


    /**
     * 支付成功
     *
     * @param outTradeNo 商户订单号
     */
    void doPaySuccess(String outTradeNo);

    /**
     * 支付中
     **/
    void doPayIng(String outTradeNo);

    /**
     * 支付失败
     *
     * @param outTradeNo 商户订单号
     */
    void doPayFail(String outTradeNo);

    /**
     * 退款成功
     *
     * @param outTradeNo 商户订单号
     */
    void doRefundSuccess(String outTradeNo);

    /**
     * 退款失败
     *
     * @param outTradeNo 商户订单号
     */
    void doRefundFail(String outTradeNo);
}
