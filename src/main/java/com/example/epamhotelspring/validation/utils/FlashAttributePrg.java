package com.example.epamhotelspring.validation.utils;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Class for transporting errors with Post-Redirect-Get pattern
 */
public class FlashAttributePrg {

    public final static String BINDING_RESULT_CLASS_PATH = "org.springframework.validation.BindingResult.";

    private final BindingResult bindingResult;

    private final RedirectAttributes redirectAttributes;

    private final String formName;

    private final Object form;

    /**
     * @param bindingResult Binding result of form
     * @param redirectAttributes Redirect Attributes in which errors will be transported
     * @param formName Name of the form
     * @param form The form itself
     */
    public FlashAttributePrg(BindingResult bindingResult, RedirectAttributes redirectAttributes, String formName, Object form) {
        this.bindingResult = bindingResult;
        this.redirectAttributes = redirectAttributes;
        this.formName = formName;
        this.form = form;
    }

    /**
     * if binding result has errors, adds errors to redirect attributes and form
     * @return true if form has errors, false if not
     */
    public boolean processErrorsIfExists(){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(BINDING_RESULT_CLASS_PATH.concat(formName), bindingResult);
            redirectAttributes.addFlashAttribute(formName, form);
            return true;
        }
        return false;
    }
}
