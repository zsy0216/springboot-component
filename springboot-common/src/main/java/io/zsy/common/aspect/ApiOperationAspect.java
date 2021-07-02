package io.zsy.common.aspect;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangshuaiyin
 * @date 2021-06-01 16:23
 */
@Slf4j
@Aspect
@Component
public class ApiOperationAspect {

    @Around("@annotation(apiOperation)")
    public Object doAround(ProceedingJoinPoint joinPoint, ApiOperation apiOperation) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("======================= Request Start ==========================");
        long startTime = System.currentTimeMillis();
        log.info("Execute Method: {}.{}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("Request URL: {}", request.getRequestURI());
        log.info("Description: {}", apiOperation.value());
        log.info("Request Type: {}", request.getMethod());
        log.info("Request Params: {}", JSON.toJSONString(joinPoint.getArgs()));
        log.info("IP Address: {}", request.getRemoteAddr());
        // 执行切点方法
        Object result = joinPoint.proceed();
        log.info("Return Result: {}", result);
        log.info("Total Mills: {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    @Deprecated
    public static String getIpAddress(HttpServletRequest request) {
        String realIP = request.getHeader("Activity-IP");
        if (StringUtils.isNotBlank(realIP) && !"unknown".equalsIgnoreCase(realIP)) {
            return realIP;
        } else {
            String xIP = request.getHeader("X-REAL-IP");
            String xFor = request.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isNotBlank(xFor) && !"unknown".equalsIgnoreCase(xFor)) {
                int index = xFor.indexOf(",");
                return index != -1 ? xFor.substring(0, index) : xFor;
            } else {
                xFor = xIP;
                if (StringUtils.isNotBlank(xIP) && !"unknown".equalsIgnoreCase(xIP)) {
                    return xIP;
                } else {
                    if (StringUtils.isBlank(xIP) || "unknown".equalsIgnoreCase(xIP)) {
                        xFor = request.getHeader("Proxy-Client-IP");
                    }
                    if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                        xFor = request.getHeader("WL-Proxy-Client-IP");
                    }
                    if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                        xFor = request.getHeader("HTTP_CLIENT_IP");
                    }
                    if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                        xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
                    }
                    if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                        xFor = request.getRemoteAddr();
                    }
                    return xFor;
                }
            }
        }
    }
}
