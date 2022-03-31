package com.example.epamhotelspring.aop;

import com.example.epamhotelspring.exceptions.aop.BindingResultNotFound;
import com.example.epamhotelspring.exceptions.aop.RedirectAttributesNotFound;
import com.example.epamhotelspring.validation.utils.FlashAttributePrg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import static com.example.epamhotelspring.aop.utils.AspectUtils.*;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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
        ValidateFormWithPRG annotation = getAnnotationFromJoinPoint(pjp, ValidateFormWithPRG.class);
        String methodName = ((MethodSignature) pjp.getSignature()).getMethod().getName();

        Object[] arguments = pjp.getArgs();
        Object form = arguments[annotation.formAttributeIndex()];
        BindingResult bindingResult = getArgumentByType(arguments, BindingResult.class);
        if (bindingResult == null) {
            throw new BindingResultNotFound(methodName);
        }
        RedirectAttributes redirectAttributes = getArgumentByType(arguments, RedirectAttributes.class);
        if (redirectAttributes == null) {
            throw new RedirectAttributesNotFound(methodName);
        }

        String redirectUrl = parseSpelInRedirectUrl(annotation.redirectUrlOnError(), ((CodeSignature)pjp.getSignature()).getParameterNames(), pjp.getArgs());

        FlashAttributePrg flashAttributePrg = new FlashAttributePrg(bindingResult, redirectAttributes, annotation.formName(), form);
        boolean hasErrors = flashAttributePrg.processErrorsIfExists();
        if (hasErrors) {
            return redirectUrl;
        }
        return pjp.proceed();
    }

    private String parseSpelInRedirectUrl(String url, String[] argNames, Object[] args){
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext evaluationContext = setEvalContextFromMethodArgs(argNames, args);
        Expression expression = parser.parseExpression(url);
        return (String) expression.getValue(evaluationContext);
    }

    private EvaluationContext setEvalContextFromMethodArgs(String[] argNames, Object[] args){
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++){
            evaluationContext.setVariable(argNames[i], args[i]);
        }
        return evaluationContext;
    }

}
