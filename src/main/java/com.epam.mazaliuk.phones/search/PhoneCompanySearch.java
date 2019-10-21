package com.epam.mazaliuk.phones.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCompanySearch extends BaseSearch {

    private String name;
    private Integer yearOfIssue;
}
