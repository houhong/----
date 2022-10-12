package com.houhong.thirdpay.bean;

/**
 * @program: algorithm-work
 * @description: 转账状态
 * @author: houhong
 * @create: 2022-10-06 19:40
 **/
public class TransferStatus {


    /**
     * //未知
     **/
    private static final int UNKNOWN = 0;
    /**
     * //转账成功
     **/
    private static final int TRANSFER_SUCCESS = 1;
    /**
     * //转账失败
     **/
    private static final int TRANSFER_FAIL = 2;

    /**
     * 转账中
     **/
    private static final int TRANSFER_ING = 3;

    private TransferStatus(int status, String tradeNo) {
        this.status = status;
        this.tradeNo = tradeNo;
    }

    /**
     * 订单状态
     */
    private final int status;

    /**
     * 商户订单号
     */
    private final String tradeNo;

    /**
     * 未知转账状态
     *
     * @return
     */
    public boolean isUnknown() {
        return this.status == UNKNOWN;
    }

    /**
     * 转账是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.status == TRANSFER_SUCCESS;
    }

    /**
     * 转账是否失败
     *
     * @return
     */
    public boolean isFail() {
        return this.status == TRANSFER_FAIL;
    }

    /**
     * 转账成功状态
     *
     * @param tradeNo 商户订单号
     * @return
     */
    public static TransferStatus successStatus(String tradeNo) {
        return new TransferStatus(TRANSFER_SUCCESS, tradeNo);
    }

    /**
     * 转账失败状态
     *
     * @return
     */
    public static TransferStatus failStatus(String tradeNo) {
        return new TransferStatus(TRANSFER_FAIL, tradeNo);
    }

    /**
     * 未知转账状态
     *
     * @return
     */
    public static TransferStatus unknownStatus(String tradeNo) {
        return new TransferStatus(UNKNOWN, tradeNo);
    }

    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 转账中状态
     *
     * @return
     */
    public static TransferStatus transferingStatus(String tradeNo) {
        return new TransferStatus(TRANSFER_ING, tradeNo);
    }


}