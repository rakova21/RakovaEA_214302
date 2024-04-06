package com.crewrisk.controller;

import com.crewrisk.controller.main.Attributes;
import com.crewrisk.model.Users;
import com.crewrisk.model.enums.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexCont extends Attributes {

    @GetMapping
    public String index1() {
        return "redirect:/about";
    }

    @GetMapping("/index")
    public String index2() {
        return "redirect:/about";
    }

    @GetMapping("/catalog")
    public String index2(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("humans", usersRepo.findAllByRole(Role.CLIENT));
        return "humans";
    }

    @GetMapping("/filter")
    public String filter(Model model, @RequestParam String fio) {
        AddAttributes(model);
        AddAttributesEnums(model);
        List<Users> users = usersRepo.findAllByRole(Role.CLIENT);
        users = users.stream().filter(user -> user.getPrimarys().getFio().toLowerCase().contains(fio.toLowerCase())).toList();
        model.addAttribute("humans", users);
        model.addAttribute("fio", fio);
        return "humans";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam Marital marital, @RequestParam Origin origin, @RequestParam Citizenship citizenship) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("humans", usersRepo.findAllByRoleAndTertiary_MaritalAndTertiary_OriginAndTertiary_Citizenship(Role.CLIENT, marital, origin, citizenship));
        model.addAttribute("marSelect", marital);
        model.addAttribute("oriSelect", origin);
        model.addAttribute("citSelect", citizenship);
        return "humans";
    }
}
