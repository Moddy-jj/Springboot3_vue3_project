package com.example;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.junit.jupiter.api.Test;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "zhangsan");
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//设置过期时间
                .sign(Algorithm.HMAC256("zjj"));//生成token
        System.out.println(token);
    }
    // @Test
    // public void testCheck(){
    //     String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0sImV4cCI6MTcxMjk2NTc4OH0.HqZoFF4FXgu1XvaxnEH4LBQ9UJ07hA8-Aapv_whZQLQ";
    //     JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("zjj")).build();
    //     DecodedJWT decodedJWT = jwtVerifier.verify(token);//解析token
    //     Map<String,Claim> claims = decodedJWT.getClaims();
    //     System.out.println(claims.get("user"));
    // }
}
