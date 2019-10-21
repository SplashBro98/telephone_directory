package com.epam.mazaliuk.phones.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch extends BaseSearch {

    private String firstName;
    private String lastName;
    private String city;
}
