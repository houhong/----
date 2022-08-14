package com.ohouhong.business.rocketmq.tranctions;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: algorithm-work
 * @description: todo 事务回调监听器 用来保证在分布式事务中 事务发起方的本地事务和发送Mq消息的原子性
 * @author: houhong
 * @create: 2022-08-14 20:44
 **/
public class TransactionListenerImpl implements TransactionListener {


    /**
     * 整个事务消息流程
     * 1： 首先发送half消息用于做broker 探活
     * 2：  SendResult sendResult = producer.sendMessageInTransaction(msg, null);
     * 的时候，首先会执行  executeLocalTransaction(Message msg, Object arg)这个方法
     * 这里保证了本地消息和send Msg的原子性
     * <p>
     * 然后   过一段时间 会对UNKNOW 的消息 进行回调 执行 checkLocalTransaction(MessageExt msg);
     **/
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        String tags = msg.getTags();
        if (StringUtils.contains(tags, "tagsA")) {

            //提交消息
            return LocalTransactionState.COMMIT_MESSAGE;

        } else if (StringUtils.contains(tags, "tagsB")) {

            //回滚消息
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {

            //未知消息，MQ broker 会间隔一段时间回查 本地事务状态
            return LocalTransactionState.UNKNOW;
        }

    }


    /**
     * 回查
     * 1:用来对未知消息进行做回查 在这里可以对于executeLocalTransaction 的unnknow 进行
     * 再一次执行。
     **/
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {

        String tags = msg.getTags();

        if (StringUtils.contains(tags, "TagC")) {

            return LocalTransactionState.COMMIT_MESSAGE;
        } else if (StringUtils.contains(tags, "TagC")) {

            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {
            return LocalTransactionState.UNKNOW;
        }
    }
}