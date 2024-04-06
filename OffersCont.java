package com.crewrisk.controller;

import com.crewrisk.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OffersCont extends Attributes {
    @GetMapping
    public String offers(Model model) {
        AddAttributes(model);
        model.addAttribute("offers", offersRepo.findAll());
        return "offers";
    }
}
