package com.example.demo.cat.web;

import com.example.demo.cat.CatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    CatService catService;

    public WebController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("cats")
    @PreAuthorize("hasRole('ADMIN')")
    public String cats(Model model, HttpServletRequest httpServletRequest) {
        var cats = catService.allCatNames();
        model.addAttribute("catNames", cats);
        model.addAttribute("catCount", cats.size());
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "cats";
    }

    //Controller method for showing the empty form
    @GetMapping("create")
    public String moreCats(Model model) {
        model.addAttribute("formData", new CreateCatFormData());
        return "create";
    }



    @PostMapping("create")

    public String greetingSubmit(@Valid @ModelAttribute("formData") CreateCatFormData cat,
                                 BindingResult bindingResult,
                                 Model model) {
        if( bindingResult.hasErrors() ){
            return "create";
        }

        catService.save(cat.toEntity());
        return "redirect:/web/cats";
    }
}
