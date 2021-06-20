package br.com.gsividal.o2bproject.controller;

import lombok.Data;

@Data
public class Violation {

    private final String fieldName;
    private final String message;
}