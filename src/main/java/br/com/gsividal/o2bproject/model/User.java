package br.com.gsividal.o2bproject.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @NotNull(message = "ID is mandatory")
    private Long id;

    @NotBlank(message = "Login is mandatory")
    @Column(unique = true)
    private String login;

    @NotBlank(message = "Password is mandatory")
    private String password;
}