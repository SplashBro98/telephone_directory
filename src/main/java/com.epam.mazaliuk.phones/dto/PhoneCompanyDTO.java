package com.epam.mazaliuk.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCompanyDTO {

    private Long id;
    private String name;
    private int yearOfIssue;
    private List<PhoneNumberDTO> phoneNumbers;

}
