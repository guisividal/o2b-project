package br.com.gsividal.o2bproject.controller;

import lombok.Data;

@Data
public class IllegalArgumentErrorResponse {

    private final String error;
    private final String timeStamp;
    private final String message;
}