package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Description
 * @Author 47179
 * @Date 8:51 2019/7/16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        //使用手机号和验证码登录
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //从Redis中获取缓存的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            //验证码输入正确
            //判断当前用户是否为会员
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
                //当前用户不是会员,自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //用户跟踪,写手机号码到Cookie,当用户再次访问我们的网站时就会带上这个Cookie
            //这样我们就知道是哪个用户了,方便日后做统计分析
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setMaxAge(60 * 60 * 24 * 24);//有效期
            cookie.setPath("/");//设置网站的跟路径,当访问到这个网站就会带上Cookie
            response.addCookie(cookie);//给客户端写cookie
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }

}
