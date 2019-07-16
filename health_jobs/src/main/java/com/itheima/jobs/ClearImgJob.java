package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @ClassName ClearImgJob
 * @Description
 * @Author 47179
 * @Date 11:14 2019/7/12
 * @Version 1.0
 **/
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    //清理图片
    public void clearImg() {
        System.out.println("clearImg()...");

        //计算set差值
        Set<String> set = jedisPool.getResource().sdiff((RedisConstant.SETMEAL_PIC_RESOURCES), RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String pic : set) {
            //删除
            //删除七牛云上的
            QiniuUtils.deleteFileFromQiniu(pic);
            //删除redis集合中的
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
        }
    }
}
