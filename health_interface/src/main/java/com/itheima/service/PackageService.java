package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Package;

import java.util.List;

public interface PackageService {
    void add(Package pkg, Integer[] checkgroupIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    public List<Package> findAll();

    Package findById(int id);

}
