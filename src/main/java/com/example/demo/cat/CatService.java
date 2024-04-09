package com.example.demo.cat;

import com.example.demo.MailSenderService;
import com.example.demo.message.MessageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepository catRepository;
    private final MessageService messageService;
    private final MailSenderService mailSenderService;

    public CatService(CatRepository catRepository, MessageService messageService, MailSenderService mailSenderService){
        this.catRepository = catRepository;
        this.messageService = messageService;
        this.mailSenderService = mailSenderService;
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
        messageService.sendMessage("A new cat named " + cat.getName() + " was created at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ROOT)));
    }
}
