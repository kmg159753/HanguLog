package com.example.hangusemiproject.desk.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException  {
    private String result ;
    private String msg;
    private int statusCode;
}
