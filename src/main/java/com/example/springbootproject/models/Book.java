package com.example.springbootproject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Name_of_creator should not be empty")
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]{1,15} [A-ZА-Я][a-zа-я]{1,15}",
            message = "FIO shoud be format: \"Name, Surname\"")
    @Column(name = "name_of_creator")
    private String nameOfCreator;
    @Min(value = 1900, message = "Year should be greater than 1900")
    @Max(value = 2023, message = "Year should be low than 2024")
    @Column(name = "year_of_publishing")
    private int yearOfPublishing;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;
@Column(name = "created_at")
@Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
@Transient
private boolean prosrochka;

    public boolean isProsrochka() {
        return prosrochka;
    }

    public void setProsrochka(boolean prosrochka) {
        this.prosrochka = prosrochka;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfCreator() {
        return nameOfCreator;
    }

    public void setNameOfCreator(String nameOfCreator) {
        this.nameOfCreator = nameOfCreator;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public Book(String name, String nameOfCreator, int yearOfPublishing) {
        this.name = name;
        this.nameOfCreator = nameOfCreator;
        this.yearOfPublishing = yearOfPublishing;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", nameOfCreator='" + nameOfCreator + '\'' +
                ", yearOfPublishing=" + yearOfPublishing +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && yearOfPublishing == book.yearOfPublishing && Objects.equals(name, book.name) && Objects.equals(nameOfCreator, book.nameOfCreator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, nameOfCreator, yearOfPublishing);
    }
}
