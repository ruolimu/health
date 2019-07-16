package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrdersettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName OrdersettingServiceImpl
 * @Description 预约设置服务
 * @Author 47179
 * @Date 15:34 2019/7/12
 * @Version 1.0
 **/
@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService{

    @Autowired
    private OrdersettingDao ordersettingDao;

    //批量添加
    @Override
    @Transactional
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //检查次数据(日期)是否存在
                //long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());//bug
                OrderSetting os = ordersettingDao.findByOrderDate(orderSetting.getOrderDate());
                if (null != os) {
                    //已经存在,执行更新操作
                    ordersettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    //不存在,执行添加操作
                    ordersettingDao.add(orderSetting);
                }

            }
        }
    }

    @Override
    public List<OrderSetting> getOrderSettingByMonth(String date) {//2019-8
        String dateBegin = date + "-1";//2019-8-1
        String dateEnd = date + "-31";//2019-8-31
        List<OrderSetting> list = ordersettingDao.getOrderSettingByMonth(dateBegin, dateEnd);
        return list;
    }

    @Override
    @Transactional
    public void editNumberByDate(OrderSetting orderSetting) {
        try {
            //根据日期修改可预约人数
            //int count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            OrderSetting os = ordersettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (null != os) {
                //当前日期已经进行了预约设置，需要进行修改操作
                ordersettingDao.editNumberByDate(orderSetting);
            } else {
                //当前日期没有进行预约设置，进行添加操作
                ordersettingDao.add(orderSetting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
