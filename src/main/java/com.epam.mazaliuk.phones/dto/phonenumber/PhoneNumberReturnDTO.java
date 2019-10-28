package com.epam.mazaliuk.phones.dto.phonenumber;

import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyReturnDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberReturnDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 40)
    private String number;

    @Valid
    private PhoneCompanyReturnDTO companyDTO;
}
