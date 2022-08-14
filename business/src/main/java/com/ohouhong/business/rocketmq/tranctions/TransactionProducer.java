package com.ohouhong.business.rocketmq.tranctions;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @program: algorithm-work
 * @description: 事务消息生产者
 * @author: houhong
 * @create: 2022-08-14 20:56
 **/
public class TransactionProducer {


    public static void main(String[] args) throws Exception {


        TransactionListener transactionListener = new TransactionListenerImpl();


        /**
         *
         *  专门的事务生产者
         *
         **/
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("please_rename_unique_group_name");
        transactionMQProducer.setNamesrvAddr("192.168.1.5:9876");

        ExecutorService executorService = new ThreadPoolExecutor(2,
                5,
                100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                });

        //设置属性
        transactionMQProducer.setExecutorService(executorService);
        transactionMQProducer.setTransactionListener(transactionListener);
        transactionMQProducer.start();


        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};

        for (int i = 0; i < 10; i++) {
            try {
                Message msg =
                        new Message("TopicTest", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                //发送事务消息
                SendResult sendResult =   transactionMQProducer.sendMessageInTransaction(msg,null);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        transactionMQProducer.shutdown();


    }


}