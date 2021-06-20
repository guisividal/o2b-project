package br.com.gsividal.o2bproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotNull(message = "ID is mandatory")
    private Long id;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Login is mandatory")
    private String login;
}
