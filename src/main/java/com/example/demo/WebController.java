package com.example.demo;

import com.example.demo.cat.CatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    CatService catService;

    public WebController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/web/cats")
    public String cats(Model model, HttpServletRequest httpServletRequest) {
        var cats = catService.allCatNames();
        model.addAttribute("catNames", cats);
        model.addAttribute("catCount", cats.size());
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "cats";
    }
}
