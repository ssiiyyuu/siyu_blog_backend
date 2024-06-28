package com.siyu.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.common_utils.CastUtil;
import com.siyu.common_utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

 
@Component
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {

    private static final String BACK_END = "backend";

    private static final String FRONT_END = "frontend";


    public AuthorizationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("type");
    }
 
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String path = request.getPath().value();

            //信息直接放行
            if(path.equals("/service_admin/login") || 
               path.equals("/service_admin/info")   ||
               path.equals("/service_admin/logout")) {
                return chain.filter(exchange);
            }
            switch (config.getType()) {
                //需要鉴权
                case BACK_END:
                    HttpMethod method = request.getMethod(); 
                    if(method == null) {
                        return errorResponse(response, ExceptionEnum.ERROR);
                    }
                    String token = request.getHeaders().getFirst("X-token");
                    Map<String, Object> map =  new HashMap<>();
                    try {
                        map = JwtUtil.parseToken(token);
                    } catch(ExpiredJwtException e) {
                        return errorResponse(response, ExceptionEnum.EXPIRE_TOKEN);
                    }
                    if(map.isEmpty()) {
                        return errorResponse(response, ExceptionEnum.ILLEGAL_TOKEN);
                    }
                    List<String> roles = CastUtil.object2List(map.get("roles"), String.class);

                    if(method.equals(HttpMethod.GET)) { //如果是GET请求 能解析出token就放行
                        return chain.filter(exchange);
                    } else { //其余请求 需要admin权限
                        if(roles.get(0).equals("admin")) {
                            return chain.filter(exchange);
                        } else {
                            return errorResponse(response, ExceptionEnum.NO_PERMISSION);
                        }
                    }
                
                //无需鉴权
                case FRONT_END:
                    return chain.filter(exchange);
                default:
                    break;
            }
            return chain.filter(exchange);
        };
    }
 
 
    @Getter
    @Setter
    public static class Config {
        private String type;
    }

    public Mono<Void> errorResponse(ServerHttpResponse response, ExceptionEnum exceptionEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("code", exceptionEnum.getCode());
        map.put("message", exceptionEnum.getMessage());
        byte[] bytes = JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));

    }
}
