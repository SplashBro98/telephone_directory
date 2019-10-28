package com.epam.mazaliuk.phones.dto.phonecompany;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCompanyMainReturnDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 40)
    private String name;

    @Min(value = 1900)
    @Max(value = 2019)
    private int yearOfIssue;

    private List<@Valid PhoneNumberReferenceDTO> phoneNumbers;
}
