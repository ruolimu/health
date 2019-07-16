package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {

    void add(List<OrderSetting> orderSettingList);

    List<OrderSetting> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);

}
