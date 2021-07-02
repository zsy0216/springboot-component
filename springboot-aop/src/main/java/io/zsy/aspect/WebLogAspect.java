package io.zsy.aspect;

import com.alibaba.fastjson.JSONObject;
import io.zsy.annotation.WebLogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * AOP 日志通知
 *
 * @author zhangshuaiyin
 */
@Aspect
@Slf4j
@Component
public class WebLogAspect {

    Long startTime;

    @Pointcut("@annotation(io.zsy.annotation.WebLogAnnotation)")
    public void webLog() {
    }

    /**
     * 环绕通知
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("********** 环绕通知日志打印 **********");
        startTime = System.currentTimeMillis();
        // 执行切点
        Object result = pjp.proceed();
        log.info("返回结果: {}", result);
        log.info("耗时: {}ms", System.currentTimeMillis() - startTime);
        log.info("********** 环绕通知日志结束 **********");
        return result;
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     * @throws Exception
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        log.info("********** 前置通知日志打印 **********");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 获取 @WebLogAnnotation 注解的描述信息
        String aspectLogDescription = getAspectLogDescription(joinPoint);

        assert request != null;
        log.info("请求Url: {}", request.getRequestURI());
        log.info("注解描述: {}", aspectLogDescription);
        log.info("请求方式: {}", request.getMethod());
        log.info("执行方法: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("请求IP: {}", request.getRemoteAddr());
        log.info("请求参数: {}", JSONObject.toJSONString(joinPoint.getArgs()));

        log.info("********** 前置通知日志 END **********");
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() {
        log.info("********** 后置通知日志打印 **********");
        log.info("切点方法执行结束后执行");
        log.info("********** 后置通知日志 END **********");
    }

    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(WebLogAnnotation.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }
}
