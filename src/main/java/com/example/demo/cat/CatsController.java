package com.example.demo.cat;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
public class CatsController {

    private final CatRepository repository;
    private final CatService catService;

    public CatsController(CatRepository repository, CatService catService) {
        this.repository = repository;
        this.catService = catService;
    }

    @GetMapping("/cats")
    public List<Cat> cats() {
        var cats = repository.findAllBy().stream()
                .peek(cat -> cat.setName(HtmlUtils.htmlEscape(cat.getName())))
                .toList();
        System.out.println("Done reading cats from database!");
        return cats;
    }

    @GetMapping("/cats/{id}")
    @Cacheable("cats")
    public CatNameAndAge one(@PathVariable Long id) {
        return catService.findOneById(id).orElse(null);
    }


    @PostMapping("/cats")
    public ResponseEntity<Void> createCat(@RequestBody Cat cat) {
        catService.save(cat);

        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/cats/{id}")
//    ResponseEntity<Void> updateCat(@RequestBody Cat cat, @PathVariable Long id) {
//        var cat1 = repository.findById(id);
//        if (cat1.isPresent()) {
//            repository.save(cat);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PutMapping("/cats/{id}/chipped")
    ResponseEntity<Void> setChipped(@PathVariable Long id) {
        var count = repository.setChipped(id);
        if (count == 1)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
