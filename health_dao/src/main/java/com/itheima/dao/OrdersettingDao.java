package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrdersettingDao {
    void add(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void editNumberByDate(OrderSetting orderSetting);

    public void editReservationsByOrderDate(OrderSetting orderSetting);

    int findCountByOrderDate(Date orderDate);

    OrderSetting findByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);

}
