package com.example.demo.cat;

import com.example.demo.MailSenderService;
import com.example.demo.message.MessageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepository catRepository;
    private final MessageService messageService;

    public CatService(CatRepository catRepository, MessageService messageService){
        this.catRepository = catRepository;
        this.messageService = messageService;
    }

    @Cacheable("catNames")
    public List<String> allCatNames(){
       return catRepository.findAll().stream().map(Cat::getName).toList();
    }

    public Optional<CatNameAndAge> findOneById(Long id) {
        return catRepository.findById(id).map(cat -> new CatNameAndAge(cat.getName(), cat.getAge()));
    }

    @CacheEvict(value = "catNames", allEntries = true)
    public void save(Cat cat) {
        catRepository.save(cat);
        messageService.sendMessage("Hello " + cat.getName());
    }
}
