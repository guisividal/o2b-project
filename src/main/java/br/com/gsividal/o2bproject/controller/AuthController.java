package br.com.gsividal.o2bproject.controller;

import br.com.gsividal.o2bproject.dto.AuthRequestDTO;
import br.com.gsividal.o2bproject.model.User;
import br.com.gsividal.o2bproject.repository.UserRepository;
import br.com.gsividal.o2bproject.service.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO authRequestDto) {
        // todo: encrypt password
        User user = userRepository.findByLoginAndPassword(authRequestDto.getLogin(), authRequestDto.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("Wrong login or password"));

        String jwt = JWTUtils.createJWT(user.getId().toString(), "O2B", user.getLogin(), LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond());
        return ResponseEntity.ok(Map.of("jwt", jwt));
    }

}
