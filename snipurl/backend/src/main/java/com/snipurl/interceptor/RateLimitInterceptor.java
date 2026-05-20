package com.snipurl.interceptor;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;
import java.util.Map;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class RateLimitInterceptor implements HandlerInterceptor{
    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    private Bucket creatNewBucket(){
            Bandwidth limit = Bandwidth.classic(5, 
                 Refill.greedy(5, // 5 requests
                Duration.ofMinutes(1)));// per minute

                return Bucket.builder().addLimit(limit).build();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

                // get client Ip
                String ip = request.getRemoteAddr();

                // get BUcket for that Ip
                Bucket bucket = bucketCache.computeIfAbsent(ip, k -> creatNewBucket());

                // consume bucket
                if(bucket.tryConsume(1)) return true;

                // limit exceeded
                response.setStatus(429);
                response.getWriter().write("Too many requests");

                return false;

    }


}
