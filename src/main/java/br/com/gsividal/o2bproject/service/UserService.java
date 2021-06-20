package br.com.gsividal.o2bproject.service;

import br.com.gsividal.o2bproject.model.User;
import br.com.gsividal.o2bproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if(userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login already used: " + user.getLogin());
        }
        user.setId(null);
        // todo: encrypt password
        return userRepository.save(user);
    }

    public Optional<User> readUser(Long id) {
        log.info("Searching user with id: {}", id);
        return userRepository.findById(id);
    }

    public Optional<User> updatePassword(User userUpdated) {
        log.info("Editing user with id: {}", userUpdated.getId());
        // todo: encrypt password
        return userRepository.findById(userUpdated.getId())
                .map(user -> {
                    user.setPassword(userUpdated.getPassword());
                    return user;
                })
                .map(userRepository::save);
    }

    public Optional<User> deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        return userRepository.findById(id)
                .map(userFound -> {
                    userRepository.deleteById(id);
                    return userFound;
                });
    }
}