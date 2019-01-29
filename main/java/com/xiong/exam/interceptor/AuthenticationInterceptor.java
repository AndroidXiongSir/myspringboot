package com.xiong.exam.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xiong.exam.annotation.LoginAuth;
import com.xiong.exam.annotation.Signature;
import com.xiong.exam.common.ResponseModel;
import com.xiong.exam.common.StaticConstant;
import com.xiong.exam.common.UnicomResponseEnums;
import com.xiong.exam.exception.DataNotFoundException;
import com.xiong.exam.exception.SignatureException;
import com.xiong.exam.exception.TokenNoLegalException;
import com.xiong.exam.model.Xuser;
import com.xiong.exam.service.XuserService;
import com.xiong.exam.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname AuthenticationInterceptor
 * @Author xiong
 * @Date 2019/1/25 上午11:11
 * @Description TODO
 * @Version 1.0
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    XuserService xuserService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果方法使用了LoginAuth注解
        if (method.isAnnotationPresent(LoginAuth.class)){
            String token = request.getParameter("token");//从http请求头中取出token
            LoginAuth loginAuth = method.getAnnotation(LoginAuth.class);
            if (loginAuth.required()){
                //执行认证
                if (token == null){
                    throw new TokenNoLegalException();
                }
                //获取token中的userid
                Xuser user = null;
                try{
                    Long userid = JWTUtil.getUserId(token);
                    user = xuserService.queryById(userid);
                    if (user==null){
                        throw new DataNotFoundException();
                    }
                }catch (JWTDecodeException e){
                    throw new TokenNoLegalException();
                }

                boolean isPass = JWTUtil.verify(token,user.getPassword());
                if (isPass){
                    return true;
                }else{
                    //JWT过期异常
                    String redis_jwt = redisUtil.get("JWT"+user.getId());
                    long endtime = redisUtil.getExpire("JWT"+user.getId());
                    //如果redis缓存还没到期，刷新新token给前端
                    if (redis_jwt!=null&&redis_jwt.equals(token)){
                        if (endtime>0 && redis_jwt.equals(token)){
                            String new_token = JWTUtil.sign(Long.valueOf(user.getId()),user.getPassword());
                            redisUtil.set("JWT"+user.getId(),new_token);
                            redisUtil.expire("JWT"+user.getId(),JWTUtil.EXPIRE_TIME*2/1000,TimeUnit.SECONDS);
                            ResponseModel model = new ResponseModel().failure(UnicomResponseEnums.TOKEN_HAS_RESET,new_token);;
                            JsonUtil.sendJsonMessage(response,model);
                            return false;
                        }else if (endtime==-2){//这就代表redis缓存都过期了，直接让它重新登入
                            throw new TokenExpiredException("JWT已过期");
                        }
                    }else{
                        throw new TokenNoLegalException();
                    }

                }

            }
        }

        //如果包含签名注解
        if (method.isAnnotationPresent(Signature.class)){
            try {
                if (!SignUtils.verificationSign(request,StaticConstant.ACCESS_SECRET)) {
                    throw new SignatureException();
                }else{
                    return true;
                }
            } catch (UnsupportedEncodingException e) {
                throw new SignatureException();
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
