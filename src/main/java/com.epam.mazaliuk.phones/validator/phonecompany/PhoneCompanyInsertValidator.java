package com.epam.mazaliuk.phones.validator.phonecompany;

import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.exception.PhoneCompanyException;
import com.epam.mazaliuk.phones.exception.UserException;
import com.epam.mazaliuk.phones.repository.PhoneCompanyRepository;
import com.epam.mazaliuk.phones.specification.phonecompany.PhoneCompanyFindByNameSpecification;
import com.epam.mazaliuk.phones.util.StringUtils;
import com.epam.mazaliuk.phones.validator.EntityValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class PhoneCompanyInsertValidator implements EntityValidator<PhoneCompanyEntity> {

    private final PhoneCompanyRepository phoneCompanyRepository;

    @Override
    public void validate(PhoneCompanyEntity phoneCompanyEntity) {

        String name = phoneCompanyEntity.getName();

        if (StringUtils.isEmpty(name)) {
            throw new PhoneCompanyException("Name of the company is empty");
        }

        if (name.length() > 40) {
            throw new PhoneCompanyException("Name: " + name + " is too long");
        }

        Optional<PhoneCompanyEntity> phoneCompany = phoneCompanyRepository
                .findSingle(new PhoneCompanyFindByNameSpecification(name));

        if (phoneCompany.isPresent()) {
            throw new UserException("Phone Company with name: " + name + " is already exist");
        }
    }
}
