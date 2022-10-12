package com.houhong.thirdpay.core;

import com.houhong.thirdpay.core.common.AbstractPayManager;
import com.houhong.thirdpay.core.common.PayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: algorithm-work
 * @description: 统一支付辅助工具
 * @author: houhong
 * @create: 2022-10-06 18:57
 **/
public class PayManagers {


    private static final List<PayManager> PAY_MANAGERS = new ArrayList<PayManager>();

    /**
     * 注册调度器
     * @param manager 调度器
     */
    public static void register(PayManager manager){
        PAY_MANAGERS.add(manager);
    }

    /**
     * 根据订单号匹配调度器
     * @param outTradeNo 商户订单号
     * @return
     */
    public static PayManager find(String outTradeNo){
        for(PayManager payManager : PAY_MANAGERS){
            if(!(payManager instanceof AbstractPayManager)){
                continue;
            }
            if(((AbstractPayManager) payManager).matches(outTradeNo)){
                return payManager;
            }
        }

        throw new RuntimeException("none PayManager can be matched by outTradeNo[" + outTradeNo + "]!");
    }
}