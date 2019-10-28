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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true, length = 40)
    private String userName;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "city", nullable = false, length = 40)
    private String city;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<PhoneNumberEntity> phoneNumbers;

    public void addNumber(PhoneNumberEntity phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public void removeNumber(PhoneNumberEntity phoneNumber) {
        phoneNumbers.remove(phoneNumber);
    }

    @PreRemove
    public void clearNumbers() {
        phoneNumbers.forEach(number -> number.setUser(null));
    }

}
