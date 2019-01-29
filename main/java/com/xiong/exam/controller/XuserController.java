package com.xiong.exam.controller;

import com.github.pagehelper.PageInfo;
import com.xiong.exam.annotation.LoginAuth;
import com.xiong.exam.annotation.Signature;
import com.xiong.exam.common.ResponseModel;
import com.xiong.exam.common.UnicomResponseEnums;
import com.xiong.exam.model.Xuser;
import com.xiong.exam.service.XuserService;
import com.xiong.exam.service.imp.XuserServiceImpl;
import com.xiong.exam.utils.JWTUtil;
import com.xiong.exam.utils.RedisUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname XuserController
 * @Author xiong
 * @Date 2019/1/24 下午4:13
 * @Description TODO
 * @Version 1.0
 **/
@RestController
public class XuserController {

    @Autowired
    private XuserService xuserService;

    @Autowired
    private RedisUtil redisUtil;

    @LoginAuth
    @RequestMapping("/getAllUser")
    public ResponseModel getUser(Xuser xuser){
        List<Xuser> user =  xuserService.queryListByWhere(xuser);
        return new ResponseModel().success(user);
    }

    @GetMapping("/login")
    public ResponseModel login(Xuser xuser){
        Xuser user = xuserService.queryOne(xuser);
        if (user==null){
            return new ResponseModel().failure(UnicomResponseEnums.RESULE_DATA_NONE);
        }
        String token = JWTUtil.sign(Long.valueOf(user.getId()),user.getPassword());
        int userid = user.getId();
        long refreshPeriodTime = JWTUtil.EXPIRE_TIME*2/1000;
        redisUtil.set("JWT"+userid,token);
        redisUtil.expire("JWT"+userid,refreshPeriodTime,TimeUnit.SECONDS);
        return new ResponseModel().success(token);
    }


}
