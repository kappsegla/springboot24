package com.example.demo;

import com.example.demo.cat.CatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest
@EnableAutoConfiguration(exclude = CacheAutoConfiguration.class)
public class FullTest {

    @MockBean
    CatRepository catRepository;

    @Test
    void runEverything() {

    }
}
