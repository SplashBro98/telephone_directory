package com.epam.mazaliuk.phones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phone_company")
public class PhoneCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, length = 40)
    private String name;

    @Column(name = "year_of_issue")
    private int yearOfIssue;

    @OneToMany(mappedBy = "phoneCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneNumberEntity> phoneNumbers;

    public void addNumber(PhoneNumberEntity phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public void removeNumber(PhoneNumberEntity phoneNumber) {
        phoneNumbers.remove(phoneNumber);
    }

}
