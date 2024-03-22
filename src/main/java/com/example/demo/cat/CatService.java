package com.example.demo.cat;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CatService {

    CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Cacheable("catNames")
    public List<String> allCatNames() {
        return catRepository.findAll().stream().map(Cat::getName).toList();
    }

    public Optional<CatNameAndAge> findOneById(Long id) {
        return catRepository.findById(id).map(cat -> new CatNameAndAge(cat.getName(), cat.getAge()));
    }

    @CacheEvict(value = "catNames", allEntries = true)
    public void save(Cat cat) {
        catRepository.save(cat);
    }

    public List<Cat> allCats() {
        return catRepository.findAll();
    }

    public List<Cat> getPage(int p, int i) {
//        return catRepository.findAll(PageRequest.of(p,i)).toList();
        return catRepository.findCatsBy(p,i);
    }
}
