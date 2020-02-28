package com.purgerteam.log.trace.starter.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 用于处理所有方法的拦截
 */
@Aspect
@Component
public class TraceHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(TraceHandler.class);


    @Pointcut("execution(* cn.luischen..*.*(..)) " +
            "&& (bean(*Controller) " +
            "|| bean(*Service) " +
            "|| bean(*Impl) " +
            "|| bean(*Mapper))")
    public void pointcut(){}


    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint){

            if (LOGGER.isInfoEnabled()){
                LOGGER.info("CLASS_METHOD : {}, 入参 : {}" , joinPoint.getSignature().getDeclaringTypeName()
                        + "." + joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
            }
    }


    @AfterReturning(returning = "response",pointcut = "pointcut()")
    public void doAfterReturning(JoinPoint joinPoint,Object response){

        // 处理完请求，返回内容
        LOGGER.info("CLASS_METHOD : {}, 返回值 : {}", joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName(), response);
    }


    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around("pointcut()")
    public Object timeAround(ProceedingJoinPoint joinPoint) {

        //获取开始执行的时间
        long startTime = System.currentTimeMillis();

        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        //Object[] args = joinPoint.getArgs();

        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            LOGGER.error("=====>统计某方法执行耗时环绕通知出错", e);
        }
        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        //MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 打印耗时的信息
        LOGGER.info("CLASS_METHOD : {},处理本次请求共耗时：{} ms", joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName() ,endTime-startTime);
        return obj;
    }

}
