package com.example.demo.cat;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CatService {

    CatRepository catRepository;

    public CatService(CatRepository catRepository){
        this.catRepository = catRepository;
    }

    @Cacheable("catNames")
    public List<String> allCatNames(){
       return catRepository.findAll().stream().map(Cat::getName).toList();
    }

    public Optional<CatNameAndAge> findOneById(Long id) {
        return catRepository.findById(id).map(cat -> new CatNameAndAge(cat.getName(), cat.getAge()));
    }
}
