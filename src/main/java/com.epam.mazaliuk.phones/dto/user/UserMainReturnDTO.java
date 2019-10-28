package com.epam.mazaliuk.phones.dto.user;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMainReturnDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 40)
    private String userName;

    @NotNull
    @Size(max = 40)
    private String firstName;

    @NotNull
    @Size(max = 40)
    private String lastName;

    @Size(max = 40)
    private String city;

    private List<@Valid PhoneNumberReturnDTO> phoneNumbers;

}
