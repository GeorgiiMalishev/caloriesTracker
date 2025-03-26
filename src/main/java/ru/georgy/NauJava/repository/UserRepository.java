package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.georgy.NauJava.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}