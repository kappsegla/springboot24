package com.example.demo.cat.web;

import com.example.demo.cat.Cat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCatFormData {
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Min(1)
    @Max(50)
    private int age;

    public CreateCatFormData() {
    }

    public CreateCatFormData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Cat toEntity() {
        var cat = new Cat();
        cat.setName(name);
        cat.setAge(age);
        return cat;
    }
}
