package br.com.gsividal.o2bproject.repository;

import br.com.gsividal.o2bproject.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);
}
