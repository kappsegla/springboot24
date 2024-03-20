package com.example.demo.cat;

import java.io.Serializable;

public record CatNameAndAge(String name, int age) implements Serializable {
}
