package me.dio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.domain.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    boolean existsByName(String name);
}
