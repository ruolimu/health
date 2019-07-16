package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.PackageDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName PackageServiceImpl
 * @Description
 * @Author 47179
 * @Date 20:32 2019/7/11
 * @Version 1.0
 **/
@Service(interfaceClass = PackageService.class)
@Transactional
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    @Override
    @Transactional
    public void add(Package pkg, Integer[] checkgroupIds) {
        //添加套餐
        packageDao.add(pkg);
        //设置套餐与检查组的关系t_package_chekgroup
        if (null != checkgroupIds) {
            Integer pkgId = pkg.getId();
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.setPackageAndCheckGroup(pkgId, checkgroupId);
            }
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Package> page = packageDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Package> findAll() {
        return packageDao.findAll();
    }

    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }
}
