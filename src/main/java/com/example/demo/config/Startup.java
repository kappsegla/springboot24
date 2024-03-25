package com.example.demo.config;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import com.example.demo.vaccination.Vaccination;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Configuration
@EnableJpaAuditing
@EnableCaching
public class Startup {
    @Bean
    ApplicationRunner databaseInit(CatRepository catRepository) {
        return args -> {
            System.out.println("Running");
            var result = catRepository.findByName("Misse");
            if (result.isEmpty()) {
                var cat = new Cat();
                cat.setName("Misse");
                cat.setAge(2);
                cat.addVaccination(new Vaccination());
                cat.addVaccination(new Vaccination());
                catRepository.save(cat);
            }
        };
    }
}
