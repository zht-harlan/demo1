package com.sky.Aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 自定义切面类，实现公共字段自动处理
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点：匹配所有使用了 @AutoFill 注解的方法
     * 注意：此处的切入点表达式需要根据实际情况调整
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut(){}

    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinpoint) {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        // 获取方法参数
        Object[] args = joinpoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity=args[0];
        LocalDateTime now=LocalDateTime.now();
        Long currentUserId = BaseContext.getCurrentId();

        if(operationType == OperationType.INSERT)
        {
            try{
                Method setCreatetime=entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                setCreatetime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentUserId);
                setUpdateUser.invoke(entity, currentUserId);
            }
            catch (Exception e) {
                log.error("自动填充失败，方法不存在：", e);
                throw new RuntimeException("自动填充失败，方法不存在");
            }
        }
        else if (operationType == OperationType.UPDATE)
        {
            try{

                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);

                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                setUpdateTime.invoke(entity, now);

                setUpdateUser.invoke(entity, currentUserId);
            }
            catch (Exception e) {
                log.error("自动填充失败，方法不存在：", e);
                throw new RuntimeException("自动填充失败，方法不存在");
            }

        }
    }
}
