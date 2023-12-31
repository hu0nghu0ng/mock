package com.example.newmockup.handlers;

import com.example.newmockup.exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private Integer httpCode;
    private ErrorCodes code;
    private String message;
    List<String> errors = new ArrayList<>();
}
