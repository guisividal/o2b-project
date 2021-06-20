package br.com.gsividal.o2bproject.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue
    @NotNull(message = "ID is mandatory")
    private Long id;
    @NotNull(message = "Number is mandatory")
    private Long number;
    @NotBlank(message = "CEP code is mandatory")
    private String cep;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "State is mandatory")
    private String state;
    @NotBlank(message = "Country is mandatory")
    private String country;
    @NotBlank(message = "Street name is mandatory")
    private String streetName;
    @NotBlank(message = "Neighbourhood is mandatory")
    private String neighbourhood;

}