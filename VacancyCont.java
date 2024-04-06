package com.crewrisk.controller;

import com.crewrisk.controller.main.Attributes;
import com.crewrisk.model.Vacancy;
import com.crewrisk.model.enums.VacancyStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyCont extends Attributes {

    @GetMapping
    public String Vacancy(Model model) {
        AddAttributes(model);
        List<Vacancy> vacancies = vacancyRepo.findAllByStatus(VacancyStatus.WAITING);
        vacancies.addAll(vacancyRepo.findAllByStatus(VacancyStatus.APPROVED));
        vacancies.addAll(vacancyRepo.findAllByStatus(VacancyStatus.REJECTED));
        model.addAttribute("vacancies", vacancies);
        return "vacancy";
    }

    @GetMapping("/under_consideration/{id}")
    public String VacancyUnder_consideration(@PathVariable Long id) {
        Vacancy vacancy = vacancyRepo.getReferenceById(id);
        vacancy.setStatus(VacancyStatus.APPROVED);
        vacancyRepo.save(vacancy);
        return "redirect:/vacancy";
    }

    @GetMapping("/rejected/{id}")
    public String VacancyRejected(@PathVariable Long id) {
        Vacancy vacancy = vacancyRepo.getReferenceById(id);
        vacancy.setStatus(VacancyStatus.REJECTED);
        vacancyRepo.save(vacancy);
        return "redirect:/vacancy";
    }
}
