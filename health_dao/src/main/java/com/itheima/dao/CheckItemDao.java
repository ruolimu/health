package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findAllByCondition(String queryString);

    CheckItem findByid(int id);

    void edit(CheckItem checkItem);

    void deleteById(Integer id);

    long findCountByCheckItemId(Integer checkItemId);

    List<CheckItem> findAll();

}
