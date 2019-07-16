package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrdersettingService;
import com.itheima.util.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderSettingController
 * @Description 预约
 * @Author 47179
 * @Date 15:18 2019/7/12
 * @Version 1.0
 **/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrdersettingService ordersettingService;

    /**
     * @author RuoLi
     * @Description 文件上传, 解析并保存到数据库
     * @Date 15:20 2019/7/12
     * @Param
     * @Return
     **/
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //读取Excel文件
            List<String[]> list = POIUtils.readExcel(excelFile);
            OrderSetting orderSetting= null;
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (String[] strings : list) {
                    orderSetting = new OrderSetting(
                            /*new Date(strings[0]*/
                            sdf.parse(strings[0]),
                            Integer.parseInt(strings[1])
                    );
                    orderSettingList.add(orderSetting);
                }
                ordersettingService.add(orderSettingList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @PostMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam String date) {//date参数格式为 2019-08
        try {
            List<OrderSetting> list = ordersettingService.getOrderSettingByMonth(date);
            //获取预约设置数据成功
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            //获取预约设置数据失败
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            ordersettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //预约设置失败
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
