package com.itheima.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckItemServiceImpl
 * @Description
 * @Author 47179
 * @Date 22:14 2019/7/6
 * @Version 1.0
 **/
@Service(interfaceClass=CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        //有查询条件 模糊查询语句处理
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        //分页插件
        PageHelper.startPage(currentPage, queryPageBean.getPageSize());
        //开始查询
        Page<CheckItem> page = checkItemDao.findAllByCondition(queryPageBean.getQueryString());
        //用PageResult封装结果
        PageResult result = new PageResult(page.getTotal(), page.getResult());
        return result;
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findByid(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public void delete(Integer id) throws RuntimeException{
        //查询当前检查项是否和检查组关联,判断当前的检查项是否有检查组在用它
        //通过查询t_checkgroup_checkitem where chectitem_id=要删除的编号
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count > 0){
            //当前检查项被引用，不能删除
            //自定义异常	使用场景:用于终止已知不符合业务逻辑的操作继续执行
            throw new RuntimeException("DELETE_CHECKITEM_FAIL_USE");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
