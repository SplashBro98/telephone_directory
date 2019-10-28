package com.epam.mazaliuk.phones.dto.phonenumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberCreateDTO {

    @NotNull
    @Size(max = 40)
    private String number;

}
