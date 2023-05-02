package com.example.springbootproject.repositories;

import com.example.springbootproject.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    List<Person> findByFio(String fio);
    List<Person> findByFioOrderByYearOfBirds(String fio);
    List<Person> findByFioStartingWith(String startingWith);
    List<Person> findByFioOrYearOfBirds(String fio, int yearOfBirds);
}
