package com.houhong.paygetway.api;


import com.houhong.paygetway.domain.dto.*;

import java.util.List;

/**
 * 支付业务接口
 **/
public interface IPayAgent {

    /**
     * 预支付接口
     */
    Result<PayRequestResultDTO> pay(PayRequestDTO payRequestDTO);

    /**
     * 支付结果查询
     */
    Result<PayQueryResultDTO> payQuery(PayQueryDTO payQueryDTO);

    /**
     * 退款申请
     */
    Result<RefundApplyResultDTO> refundApply(RefundApplyDTO refundApplyDTO);

    /**
     * 退款结果查询
     */
    Result<List<RefundQueryResultDTO>> refundQuery(RefundQueryDTO refundQueryDTO);

    /**
     * 关闭订单
     */
    Result<CloseOrderResultDTO> closeOrder(CloseOrderDTO closeOrderDTO);

    /**
     *  商户接入
     **/


}
