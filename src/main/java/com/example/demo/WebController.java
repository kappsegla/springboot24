package com.example.demo;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    CatRepository catRepository;

    public WebController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping("/web/cats")
    public String cats(Model model, HttpServletRequest httpServletRequest) {
        var cats = catRepository.findAll().stream().map(Cat::getName).toList();
        model.addAttribute("catNames", cats);
        model.addAttribute("catCount", cats.size());
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "cats";
    }
}
