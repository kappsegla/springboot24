package com.example.demo.cat.web;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import com.example.demo.cat.CatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class WebController {

    CatService catService;
    private final CatRepository catRepository;

    public WebController(CatService catService,
                         CatRepository catRepository) {
        this.catService = catService;
        this.catRepository = catRepository;
    }

    @GetMapping("cats")
    public String cats(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("nextpage", 1);
        model.addAttribute("cats", catService.getPage(0, 10));
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "cats/cats";
    }

    @GetMapping("cats/nextpage")
    public String loadMore(Model model, @RequestParam(defaultValue = "1") String page) {
        int p = Integer.parseInt(page);
        model.addAttribute("nextpage", p + 1);
        model.addAttribute("cats", catService.getPage(p, 10));
        return "cats/nextpage";
    }

    //Controller method for showing the empty form
    @GetMapping("create")
    public String moreCats(Model model) {
        model.addAttribute("formData", new CreateCatFormData());
        return "cats/create";
    }

    @PostMapping("create")
    public String greetingSubmit(@Valid @ModelAttribute("formData") CreateCatFormData cat,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "cats/create";
        }

        catService.save(cat.toEntity());
        return "redirect:/web/cats";
    }

    @ResponseBody
    @DeleteMapping(path = "/cats/{catId}", produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long catId) {
        catRepository.findById(catId).ifPresent(catRepository::delete);
        return "";
    }


    @GetMapping("/cats/{id}")
    public String oneCat(@PathVariable Long id, Model model) {
        var cat = catRepository.findById(id).get();
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);
        return "cats/click-to-edit-default";
    }

    @PostMapping("/cats/{id}/edit")
    public String editForm(Model model, @PathVariable Long id) {
        var cat = catRepository.findById(id).get();
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);
        return "cats/click-to-edit-form";
    }

    @PutMapping("/cats/{id}")
    public String editPost(@ModelAttribute Cat cat, Model model) {
        catRepository.save(cat);
        return "cats/click-to-edit-default";
    }
}
