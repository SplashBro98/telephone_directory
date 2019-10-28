package com.epam.mazaliuk.phones.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnDTO {

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
}
