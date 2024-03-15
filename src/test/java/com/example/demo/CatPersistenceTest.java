package com.example.demo;

import com.example.demo.cat.Cat;
import com.example.demo.cat.CatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:mysql:8.3.0:///mydatabase"
})
class CatPersistenceTest {

    @Autowired
    CatRepository catRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void setChippedChangesChippedStatusToTrue() {
//        var cat = new Cat();
//        cat.setName("Misse");
//        cat.setAge(2);
//        cat.addVaccination(new Vaccination());
//        cat.addVaccination(new Vaccination());
//        var c = catRepository.save(cat);
//        entityManager.flush();
//        entityManager.clear();
        catRepository.setChipped(1L);
        var result = catRepository.findById(1L);
        assertThat(result).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("chipped", true);
    }

    @Test
    void chippesIsFalseForDefaultCat() {
        var cat = catRepository.findById(1L);
        assertThat(cat).get().hasFieldOrPropertyWithValue("chipped", false);
    }

    @Test
    @Transactional
    public void testSetChipped() {
        // Create a Cat entity and save it
        Cat cat = new Cat();
        cat.setName("Whiskers");
        cat.setChipped(false);
        catRepository.save(cat);

        // Call the setChipped method
        int updatedRows = catRepository.setChipped(cat.getId());

        // Verify that the update was successful
        assertThat(updatedRows).isEqualTo(1);

        // Retrieve the cat again and check if chipped is true
        Cat updatedCat = catRepository.findById(cat.getId()).orElse(null);
        assertThat(updatedCat).isNotNull();
        assertThat(updatedCat.isChipped()).isTrue();
    }
}
