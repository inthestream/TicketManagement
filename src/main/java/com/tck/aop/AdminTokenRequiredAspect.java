package com.tck.aop;

import com.tck.service.SecurityServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

@Aspect
@Component
public class AdminTokenRequiredAspect {
    @Before("@annotation(adminTokenRequired)")
    public void adminTokenRequiredWithAnnotation(AdminTokenRequired adminTokenRequired) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //checks for token in request header
        String tokenInHeader = request.getHeader("token");

        if(StringUtils.isEmpty(tokenInHeader)) {
            throw new IllegalArgumentException("Empty token");
        }

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SecurityServiceImpl.secretKey))
                .parseClaimsJws(tokenInHeader).getBody();

        if(claims == null || claims.getSubject() == null) {
            throw new IllegalArgumentException("Token Error : Claims is null");
        }

        String subject = claims.getSubject();

        System.out.println(" usertype : " + subject.split("=")[1]);

        if(subject.split("=").length != 2 || new Integer(subject.split("=")[1]) != 3) {
            throw new IllegalArgumentException("User is not authorized");
        }
    }
}
