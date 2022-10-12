package com.houhong.thirdpay.core.common;

import com.houhong.thirdpay.bean.AliPayNoticeInfo;
import com.houhong.thirdpay.bean.TradeStatus;
import com.houhong.thirdpay.bean.WxPayNoticeInfo;
import com.houhong.thirdpay.bean.common.NoticeInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: algorithm-work
 * @description: 统一支付通知处理
 * @author: houhong
 * @create: 2022-10-06 17:47
 **/
public abstract class AbstractNoticeManager implements NoticeManager {

    /**
     * 回调核心实现
     */
    private static AliPayNotice aliPayNotice = new AliPayNotice();
    private static WxPayNotice wxPayNotice = new WxPayNotice();

    @Override
    public Map<String, String> receiveAliParams(HttpServletRequest request) {
        return aliPayNotice.receiveParams(request);
    }

    @Override
    public Map<String, String> receiveWxParams(HttpServletRequest request) {
        return wxPayNotice.receiveParams(request);
    }

    @Override
    public void sendAliResponse(HttpServletResponse response) {
        aliPayNotice.sendResponse(response);
    }

    @Override
    public void sendWxResponse(HttpServletResponse response) {
        wxPayNotice.sendResponse(response);
    }

    @Override
    public TradeStatus execute(Map<String, String> params, NoticeInfo info) {
        if(info instanceof AliPayNoticeInfo){
            return aliPayNotice.execute(params, (AliPayNoticeInfo) info);
        }

        if(info instanceof WxPayNoticeInfo){
            return wxPayNotice.execute(params, (WxPayNoticeInfo) info);
        }

        throw new IllegalArgumentException("不支持的通知类型[" + info.getClass().getSimpleName() + "]");
    }
}