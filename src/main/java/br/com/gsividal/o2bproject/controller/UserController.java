package br.com.gsividal.o2bproject.controller;

import br.com.gsividal.o2bproject.dto.UserDTO;
import br.com.gsividal.o2bproject.model.User;
import br.com.gsividal.o2bproject.service.JWTUtils;
import br.com.gsividal.o2bproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.gsividal.o2bproject.service.JWTUtils.extractJWT;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createUser(convert(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(convert(user));
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> readUser(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));

        return userService.readUser(id)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(convert(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // UPDATE
    @PutMapping
    public ResponseEntity<UserDTO> updatePassword(@Valid @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));

        return userService.updatePassword(convert(userDTO))
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(convert(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));

        return userService.deleteUser(id)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(convert(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    private User convert(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}