package com.example.demo.cat;

import com.example.demo.vaccination.Vaccination;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private boolean chipped = false;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cat_id")
    List<Vaccination> vaccinationList = new ArrayList<>();

    public List<Vaccination> getVaccinationList() {
        return vaccinationList;
    }

    public void setVaccinationList(List<Vaccination> vaccinationList) {
        this.vaccinationList = vaccinationList;
    }

    public boolean isChipped() {
        return chipped;
    }

    public void setChipped(boolean chipped) {
        this.chipped = chipped;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addVaccination(Vaccination vaccination){
        vaccinationList.add(vaccination);
    }

}
