package com.houhong.redisframwork.zset;

import com.houhong.redisframwork.domain.DynamicInfo;
import com.houhong.redisframwork.domain.Phone;
import com.houhong.redisframwork.domain.PhoneInfo;
import com.houhong.redisframwork.utils.Constants;

import com.houhong.redisframwork.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-26 23:37
 **/
@Service
public class PhoneServiceImpl implements PhoneService {


    List<Phone> phones = Arrays.asList(
            new Phone(1, "苹果"),
            new Phone(2, "小米"),
            new Phone(3, "华为"),
            new Phone(4, "oppo"),
            new Phone(5, "vivo"));

    Jedis jedis = null;

    @PostConstruct
    public void initJedis() {

        // 注：保持代码简介，未使用 JedisPool 生产环境 应使用连接池
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("1234");
        jedis.select(10);
    }

    @Override
    public void buyPhone(int phoneId) {

        //购买成功,对于排行榜中的score +1
        jedis.zincrby(Constants.SALES_LIST, 1, String.valueOf(phoneId));
        // 添加购买动态
        long currentTimeMillis = System.currentTimeMillis();
        String msg = currentTimeMillis + Constants.separator + phones.get(phoneId - 1).getName();
        //将字符串添加到指定的key中存储列表的头部（LPUSH）或尾部（RPUSH）
        jedis.lpush(Constants.BUY_DYNAMIC, msg);
    }

    @Override
    public List<PhoneInfo> getPhbList() {
        // 按照销量多少排行，取出前五名
        Set<Tuple> tuples =   jedis.zrevrangeWithScores(Constants.SALES_LIST, 0, 4);
       // Set<Tuple> tuples = jedis.zrevrangeByScoreWithScores(Constants.SALES_LIST, 0, 4);
        List<PhoneInfo> list = new ArrayList<PhoneInfo>();
        for (Tuple tuple : tuples) {
            PhoneInfo vo = new PhoneInfo();
            // 取出对应 phoneId 的手机名称
            int phoneId = Integer.parseInt(tuple.getElement());
            vo.setName(phones.get(phoneId - 1).getName());
            vo.setSales((int) tuple.getScore());
            list.add(vo);
        }
        return list;

    }

    @Override
    public List<DynamicInfo> getBuyDynamic() {
        List<DynamicInfo> dynamicList = new ArrayList<DynamicInfo>();
        for (int i = 0; i < 3; i++) {
            /* jedis.lindex(String key,int index)
             * 返回存储在指定键中的列表的指定元素。0是第一个元素，1是第二个元素
             */
            String result = jedis.lindex(Constants.BUY_DYNAMIC, i);
            if (StringUtils.isEmpty(result)) {
                break;
            }
            String[] arr = result.split(Constants.separator);
            long time = Long.valueOf(arr[0]);
            String phone = arr[1];
            DynamicInfo vo = new DynamicInfo();
            vo.setPhone(phone);
            vo.setTime(StringUtil.showTime(new Date(time)));
            dynamicList.add(vo);
        }

        return dynamicList;
    }

    @Override
    public int phoneRank(int phoneId) {
        // 如果是排名第一， 返回的是0 ，因此如果为null 即不在排行榜上则返回-1
        Long zrevrank = jedis.zrevrank(Constants.SALES_LIST, String.valueOf(phoneId));
        return zrevrank == null ? -1 : zrevrank.intValue();
    }

    @Override
    public void clear() {
        jedis.del(Constants.BUY_DYNAMIC);
        jedis.del(Constants.SALES_LIST);
    }

    @Override
    public void initCache() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("1", 4.0);
        map.put("2", 2.0);
        map.put("3", 3.0);
        jedis.zadd(Constants.SALES_LIST, map);
    }
}