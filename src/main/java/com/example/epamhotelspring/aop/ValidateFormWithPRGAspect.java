package com.example.epamhotelspring.aop;

import com.example.epamhotelspring.exceptions.aop.BindingResultNotFound;
import com.example.epamhotelspring.exceptions.aop.RedirectAttributesNotFound;
import com.example.epamhotelspring.validation.utils.FlashAttributePrg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class ValidateFormWithPRGAspect {

    /**
     * Validates form and returns {@link ValidateFormWithPRG#redirectUrlOnError()} string
     * if form is valid proceeds method
     */
    @Around("@annotation(ValidateFormWithPRG)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        ValidateFormWithPRG annotation = method.getAnnotation(ValidateFormWithPRG.class);
        Object[] arguments = pjp.getArgs();
        Object form = arguments[annotation.formAttributeIndex()];
        BindingResult bindingResult = getArgumentByType(arguments, BindingResult.class);
        if(bindingResult == null){
            throw new BindingResultNotFound(method.getName());
        }
        RedirectAttributes redirectAttributes = getArgumentByType(arguments, RedirectAttributes.class);
        if(redirectAttributes == null){
            throw new RedirectAttributesNotFound(method.getName());
        }

        FlashAttributePrg flashAttributePrg = new FlashAttributePrg(bindingResult, redirectAttributes, annotation.formName(), form);
        boolean hasErrors = flashAttributePrg.processErrorsIfExists();
        if(hasErrors){
            return annotation.redirectUrlOnError();
        }
        return pjp.proceed();
    }

    /**
     * Finds argument of given class or returns null
     * @param args Array of arguments
     * @param argClass Class of argument to find
     * @param <T> Type of argument
     * @return Found argument with of argClass or returns null
     */
    @Nullable
    @SuppressWarnings("unchecked")
    private <T> T getArgumentByType(Object[] args, Class<T> argClass){
        for (Object arg : args){
            if(argClass.isAssignableFrom(arg.getClass())){
                return (T) arg;
            }
        }
        return null;
    }

}
