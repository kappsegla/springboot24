package com.example.demo.cat;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatsController {

    private final CatRepository repository;

    public CatsController(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cats")
    List<Cat> cats() {
        var cats = repository.findAllBy();
        System.out.println("Done reading cats from database!");
        return cats;
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
    ResponseEntity<Void> setChipped(@PathVariable Long id){
            var count = repository.setChipped(id);
            if( count == 1)
                return ResponseEntity.noContent().build();
            else
                return ResponseEntity.notFound().build();
    }
}
