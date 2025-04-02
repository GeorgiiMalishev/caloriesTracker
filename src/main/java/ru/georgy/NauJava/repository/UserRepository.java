package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.georgy.NauJava.model.User;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

}