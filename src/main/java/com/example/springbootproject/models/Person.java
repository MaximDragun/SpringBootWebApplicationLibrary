package com.example.springbootproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;


    @Min(value = 1900, message = "Год рождения не может быть меньше 1900!")
    @Max(value = 2024, message = "Год рождения не может быть больше 2023!")
    @Column(name = "year_of_birds")
    private int yearOfBirds;

    @NotEmpty(message = "ФИО не может быть пустым!")
    @Pattern(regexp = "[А-ЯA-Z][а-яё\\w]{1,15} [А-ЯA-Z][а-яё\\w]{1,15} [А-ЯA-Z][а-яё\\w]{1,15}",
            message = "ФИО формат: \"Фамилия Имя Отчество\"")
    @Column(name = "fio")
    private String fio;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person() {

    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getYearOfBirds() {
        return yearOfBirds;
    }

    public void setYearOfBirds(int yearOfBirds) {
        this.yearOfBirds = yearOfBirds;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Person(int yearOfBirds, String fio) {
        this.yearOfBirds = yearOfBirds;
        this.fio = fio;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", yearOfBirds=" + yearOfBirds +
                ", fio='" + fio + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId && yearOfBirds == person.yearOfBirds && Objects.equals(fio, person.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, yearOfBirds, fio);
    }
}
