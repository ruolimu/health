package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiniuUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference//(check = false)
    private PackageService packageService;

    //获取所有套餐信息
    @RequestMapping("/getPackage")
    public Result getPackage(){
        try{
            List<Package> list = packageService.findAll();
            // 设置图片的链接地址
            list.forEach(pkg->{
                pkg.setImg(QiniuUtils.DOMAIN + "/" + pkg.getImg());
            });
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //根据id查询套餐信息
    @RequestMapping("findById")
    public Result findById(@RequestParam int id) {
        try{
            Package pkg = packageService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pkg);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}