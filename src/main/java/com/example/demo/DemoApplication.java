package com.example.demo;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import com.example.demo.vaccination.Vaccination;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

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
