package com.epam.mazaliuk.phones.handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private String httpStatus;
    private String message;
}
