package com.itheima.service;

import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MemberServiceImpl
 * @Description
 * @Author 47179
 * @Date 9:20 2019/7/16
 * @Version 1.0
 **/
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }
}
