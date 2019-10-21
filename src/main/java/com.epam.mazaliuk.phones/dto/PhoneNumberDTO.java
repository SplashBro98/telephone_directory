package com.epam.mazaliuk.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDTO {

    private Long id;
    private String number;
    private String phoneCompany;

}
