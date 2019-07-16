package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackageDao {
    void add(Package pkg);

    void setPackageAndCheckGroup(@Param("pkgId") Integer pkgId, @Param("checkGroupId") Integer checkgroupId);

    Page<Package> selectByCondition(String queryString);

    List<Package> findAll();

    Package findById(int id);

}
