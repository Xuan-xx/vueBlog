package com.xuan.vueblog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "config.jwt")
@Slf4j
public class JwtUtils {

    private String secret;  //密钥
    private int expire;    //超时时间(天）
    private String header;
    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {

        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        calendar.add(Calendar.DATE,this.expire);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512,this.secret)
                .compact();
    }

    // 获取jwt的信息
    public Claims getClaimByToken(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.info("token error -----> {}",e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}

