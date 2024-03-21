package com.example.demo;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class StartupRunner implements ApplicationRunner {
    private static final Logger LOG
            = Logger.getLogger(ApplicationRunner.class.getName());

    private final CatRepository catRepository;

    public StartupRunner(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Checking for Misse");
        var result =  catRepository.findByName("Misse");
        if( result.isEmpty()){
            var cat = new Cat();
            cat.setName("Misse");
            cat.setAge(2);
            catRepository.save(cat);
        }
        if (catRepository.count() < 100) {

            for (int i = 0; i < 100; i++) {
                var cat = new Cat();
                cat.setName("Cat" + i + 1);
                cat.setAge(i % 10); // Example: Setting age based on a pattern
                catRepository.save(cat);
            }
        }
    }
}
