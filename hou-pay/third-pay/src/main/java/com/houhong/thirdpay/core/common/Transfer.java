package com.houhong.thirdpay.core.common;

import com.houhong.thirdpay.bean.TransferStatus;
import com.houhong.thirdpay.bean.common.TransferTrade;
import com.houhong.thirdpay.exception.TransferException;

public interface Transfer<T extends TransferTrade> {

    /**
     * 转账
     *
     * @param transferTrade 转账订单
     */
    void transfer(T transferTrade) throws TransferException;

    /**
     * 订单转账状态查询
     *
     * @param transferTrade 转账订单，包含商户订单号(非第三方订单号)
     * @return 转账状态
     */
    TransferStatus status(T transferTrade);
}
