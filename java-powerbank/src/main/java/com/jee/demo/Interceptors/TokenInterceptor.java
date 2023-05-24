package com.jee.demo.Interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jee.demo.utils.JwtUtil;
import com.jee.demo.utils.ResultUtil;
import com.jee.demo.utils.ThreadLocalUtil;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        String token = request.getHeader("Authorization");
        //token为空或者被修改验证失败
        if(token == null||!JwtUtil.verify(token)){
            //返回失败信息
            ObjectMapper objectMapper=new ObjectMapper();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(ResultUtil.error(300,"token验证不通过！")));
            System.out.println("验证失败~");
            return false;
        }
        String userId = JwtUtil.getUserId(token);
        ThreadLocalUtil.saveUser(userId);
        //否则放行
        System.out.println("验证成功~");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.removeUser();
    }
}
