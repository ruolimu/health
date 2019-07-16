package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @ClassName PackageController
 * @Description
 * @Author 47179
 * @Date 11:29 2019/7/11
 * @Version 1.0
 **/
@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        /**
         * 对图片上传进行了优化,用Redis来清理上传了但实际没有提交表单的图片
         **/
        try {
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String newFilename = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), newFilename);
            String imgUrl = QiniuUtils.DOMAIN + "/" + newFilename;
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imgUrl);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFilename);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回图片链接地址,回显图片
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Package pkg, @RequestParam Integer[] checkgroupIds) {
        packageService.add(pkg, checkgroupIds);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pkg.getImg());
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = packageService.pageQuery(queryPageBean);
        return pageResult;
    }
}
