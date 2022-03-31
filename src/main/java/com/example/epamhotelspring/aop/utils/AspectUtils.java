package com.example.epamhotelspring.aop.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AspectUtils {

    /**
     * Finds argument of given class or returns null
     * @param args Array of arguments
     * @param argClass Class of argument to find
     * @param <T> Type of argument
     * @return Found argument with of argClass or returns null
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getArgumentByType(Object[] args, Class<T> argClass){
        for (Object arg : args){
            if(argClass.isAssignableFrom(arg.getClass())){
                return (T) arg;
            }
        }
        return null;
    }

    /**
     * Finds annotation on joinPoint, if annotation is not found returns null
     * @param joinPoint Join Point
     * @param annotationType Class of annotation to find
     * @param <T> Annotation
     * @return Annotation if it is found or null
     */
    public static <T extends Annotation> T getAnnotationFromJoinPoint(JoinPoint joinPoint, Class<T> annotationType){
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        return method.getAnnotation(annotationType);
    }

    private AspectUtils(){
        throw new UnsupportedOperationException();
    }
}
