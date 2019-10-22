package com.epam.mazaliuk.phones.validator.phonenumber;

import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.exception.PhonenNumberException;
import com.epam.mazaliuk.phones.repository.PhoneNumberRepository;
import com.epam.mazaliuk.phones.specification.phonenumber.PhoneNumberFindByNumberSpecification;
import com.epam.mazaliuk.phones.util.StringUtils;
import com.epam.mazaliuk.phones.validator.EntityValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class PhoneNumberInsertValidator implements EntityValidator<PhoneNumberEntity> {

    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public void validate(PhoneNumberEntity phoneNumberEntity) {

        String number = phoneNumberEntity.getNumber();

        if (StringUtils.isEmpty(number)) {
            throw new PhonenNumberException("number is empty");
        }

        if (number.length() > 40) {
            throw new PhonenNumberException("number: " + number + " is too long");
        }

        Optional<PhoneNumberEntity> phoneNumber = phoneNumberRepository
                .findSingle(new PhoneNumberFindByNumberSpecification(number));

        if (phoneNumber.isPresent()) {
            throw new PhonenNumberException("Phone number with the number: " + number + " is already exist");
        }
    }
}
