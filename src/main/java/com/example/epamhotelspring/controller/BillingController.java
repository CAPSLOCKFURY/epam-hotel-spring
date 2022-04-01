package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.model.Billing;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile/billings")
public class BillingController {

    private final BillingService billingService;

    @GetMapping("")
    public String getAllBillings(Model model, @AuthenticationPrincipal User user){
        List<Billing> billings = billingService.getUserBillings(user.getId());
        model.addAttribute("billings", billings);
        return "billings";
    }

    @PostMapping("/pay/{id}")
    public String payBilling(@PathVariable Long id, @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes){
        billingService.payBilling(id, user, redirectAttributes);
        return "redirect:/profile/billings";
    }

    @Autowired
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }
}