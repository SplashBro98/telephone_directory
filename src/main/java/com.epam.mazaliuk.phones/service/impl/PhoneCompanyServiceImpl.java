package com.epam.mazaliuk.phones.service.impl;

import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyCreateDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyMainReturnDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyUpdateDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberCreateDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.exception.PhoneCompanyNotFoundException;
import com.epam.mazaliuk.phones.exception.PhoneNumberNotFoundException;
import com.epam.mazaliuk.phones.exception.PhonenNumberException;
import com.epam.mazaliuk.phones.mapper.PhoneCompanyMapper;
import com.epam.mazaliuk.phones.mapper.PhoneNumberMapper;
import com.epam.mazaliuk.phones.repository.PhoneCompanyRepository;
import com.epam.mazaliuk.phones.repository.PhoneNumberRepository;
import com.epam.mazaliuk.phones.search.PhoneCompanySearch;
import com.epam.mazaliuk.phones.service.PhoneCompanyService;
import com.epam.mazaliuk.phones.specification.BaseSpecification;
import com.epam.mazaliuk.phones.specification.phonecompany.PhoneCompanyFindByNameSpecification;
import com.epam.mazaliuk.phones.specification.phonecompany.PhoneCompanyFindByYearSpecification;
import com.epam.mazaliuk.phones.specification.phonenumber.PhoneNumberFindByNumberSpecification;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import com.epam.mazaliuk.phones.util.StringUtils;
import com.epam.mazaliuk.phones.validator.EntityValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service("phoneCompanyService")
public class PhoneCompanyServiceImpl implements PhoneCompanyService {

    private final PhoneCompanyRepository phoneCompanyRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final PhoneCompanyMapper phoneCompanyMapper;

    private final PhoneNumberMapper phoneNumberMapper;

    @Qualifier("phoneCompanyInsertValidator")
    private final EntityValidator<PhoneCompanyEntity> insertValidator;

    @Qualifier("phoneNumberInsertValidator")
    private final EntityValidator<PhoneNumberEntity> numberValidator;

    @Transactional
    @Override
    public List<PhoneCompanyMainReturnDTO> find(PhoneCompanySearch phoneCompanySearch, int offset, int limit) {

        BaseSpecification<PhoneCompanyEntity> specification = BaseSpecification.empty();

        String name = phoneCompanySearch.getName();
        if (StringUtils.isNotEmpty(name)) {
            specification = specification.and(new PhoneCompanyFindByNameSpecification(name));
        }

        Integer yearOfIssue = phoneCompanySearch.getYearOfIssue();
        if (yearOfIssue != null) {
            specification = specification.and(new PhoneCompanyFindByYearSpecification(yearOfIssue));
        }

        List<PhoneCompanyEntity> companies = phoneCompanyRepository.find(specification, offset, limit);

        return phoneCompanyMapper.mapListEntityToDTO(companies);
    }

    @Transactional
    @Override
    public PhoneCompanyMainReturnDTO find(Long id) {
        Optional<PhoneCompanyEntity> phoneCompany = phoneCompanyRepository.findById(id);

        return phoneCompany
                .map(phoneCompanyMapper::map)
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));
    }

    @Transactional
    @Override
    public List<PhoneNumberReturnDTO> findPhoneNumbers(Long companyId) {
        Optional<PhoneCompanyEntity> phoneCompanyEntity = phoneCompanyRepository.getReferenceById(companyId);
        PhoneCompanyEntity company = phoneCompanyEntity
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));

        return phoneNumberMapper.mapListEntityToDTO(company.getPhoneNumbers());
    }

    @Transactional
    @Override
    public PhoneCompanyMainReturnDTO save(PhoneCompanyCreateDTO phoneCompanyDTO) {

        PhoneCompanyEntity phoneCompany = phoneCompanyMapper.map(phoneCompanyDTO);

        insertValidator.validate(phoneCompany);

        List<PhoneNumberEntity> phoneNumbers = phoneCompany.getPhoneNumbers();

        if (!CollectionUtils.isEmpty(phoneNumbers)) {
            phoneCompany.setPhoneNumbers(new ArrayList<>());
            phoneNumbers.forEach(number -> {

                numberValidator.validate(number);
                phoneCompany.addNumber(number);
                number.setPhoneCompany(phoneCompany);
            });
        }

        PhoneCompanyEntity companyFromDb = phoneCompanyRepository.save(phoneCompany);

        return phoneCompanyMapper.map(companyFromDb);
    }

    @Transactional
    @Override
    public PhoneCompanyMainReturnDTO addNumber(Long companyId, PhoneNumberCreateDTO phoneNumberDTO) {
        PhoneCompanyEntity phoneCompanyEntity = phoneCompanyRepository
                .getReferenceById(companyId)
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));

        String phoneNumber = phoneNumberDTO.getNumber();

        if (StringUtils.isEmpty(phoneNumber)) {
            throw new PhonenNumberException("Empty number");
        }

        Optional<PhoneNumberEntity> existingNumber = phoneNumberRepository
                .findSingle(new PhoneNumberFindByNumberSpecification(phoneNumber));

        if (existingNumber.isPresent()) {
            throw new PhonenNumberException("This number is already exist");
        }

        PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
        phoneNumberEntity.setNumber(phoneNumber);

        phoneCompanyEntity.addNumber(phoneNumberEntity);
        phoneNumberEntity.setPhoneCompany(phoneCompanyEntity);

        return phoneCompanyMapper.map(phoneCompanyEntity);
    }

    @Transactional
    @Override
    public void removeNumber(Long companyId, Long numberId) {

        PhoneCompanyEntity phoneCompanyEntity = phoneCompanyRepository
                .getReferenceById(companyId)
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));

        PhoneNumberEntity phoneNumberEntity = phoneNumberRepository.getReferenceById(numberId)
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));

        phoneCompanyEntity.removeNumber(phoneNumberEntity);
        phoneNumberEntity.setPhoneCompany(null);
    }

    @Transactional
    @Override
    public PhoneCompanyMainReturnDTO update(Long companyId, PhoneCompanyUpdateDTO companyDTO) {

        PhoneCompanyEntity phoneCompanyFromDb = phoneCompanyRepository
                .getReferenceById(companyId)
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));

        String name = companyDTO.getName();
        if (StringUtils.isNotEmpty(name)) {
            companyDTO.setName(name);
        }

        phoneCompanyFromDb.setYearOfIssue(companyDTO.getYearOfIssue());

        List<PhoneNumberEntity> phoneNumbers = phoneCompanyFromDb.getPhoneNumbers();
        if (CollectionUtils.isNotEmpty(phoneNumbers)) {
            phoneNumbers.forEach(number -> number.setUser(null));
        }

        phoneCompanyFromDb.setPhoneNumbers(new ArrayList<>());
        phoneNumbers = phoneNumberMapper.mapListReferenceDTOToEntity(companyDTO.getPhoneNumbers());

        if (CollectionUtils.isNotEmpty(phoneNumbers)) {
            phoneNumbers.forEach(number -> phoneCompanyFromDb.addNumber(phoneNumberRepository
                    .getReferenceById(number.getId())
                    .orElseThrow(PhoneNumberNotFoundException::new)));
        }

        return phoneCompanyMapper.map(phoneCompanyFromDb);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        PhoneCompanyEntity phoneCompany = phoneCompanyRepository
                .getReferenceById(id)
                .orElseThrow(() -> new PhoneCompanyNotFoundException("Phone company with provided ID not found"));

        phoneCompanyRepository.delete(phoneCompany);
    }
}
