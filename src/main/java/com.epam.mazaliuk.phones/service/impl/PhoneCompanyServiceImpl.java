package com.epam.mazaliuk.phones.service.impl;

import com.epam.mazaliuk.phones.dto.PhoneCompanyDTO;
import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.exception.PhoneCompanyNotFoundException;
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
import lombok.AllArgsConstructor;
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

    @Transactional
    @Override
    public List<PhoneCompanyDTO> find(PhoneCompanySearch phoneCompanySearch, int offset, int limit) {

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
    public PhoneCompanyDTO find(Long id) {
        Optional<PhoneCompanyEntity> phoneCompany = phoneCompanyRepository.findById(id);

        return phoneCompany.map(phoneCompanyMapper::map).orElseThrow(PhoneCompanyNotFoundException::new);
    }

    @Transactional
    @Override
    public List<PhoneNumberDTO> findPhoneNumbers(Long companyId) {
        Optional<PhoneCompanyEntity> phoneCompanyEntity = phoneCompanyRepository.getReferenceById(companyId);

        PhoneCompanyEntity company = phoneCompanyEntity.orElseThrow(PhoneCompanyNotFoundException::new);

        return phoneNumberMapper.mapListEntityToDTO(company.getPhoneNumbers());
    }

    @Transactional
    @Override
    public PhoneCompanyDTO save(PhoneCompanyDTO phoneCompanyDTO) {

        PhoneCompanyEntity phoneCompany = phoneCompanyMapper.map(phoneCompanyDTO);

        List<PhoneNumberEntity> phoneNumbers = phoneCompany.getPhoneNumbers();

        if (!CollectionUtils.isEmpty(phoneNumbers)) {
            phoneCompany.setPhoneNumbers(new ArrayList<>());
            phoneNumbers.forEach(number -> {

                PhoneNumberEntity phoneNumber = phoneNumberRepository
                        .findSingle(new PhoneNumberFindByNumberSpecification(number.getNumber()))
                        .orElse(number);

                phoneCompany.addNumber(phoneNumber);
                phoneNumber.setPhoneCompany(phoneCompany);
            });
        }

        PhoneCompanyEntity companyFromDb = phoneCompanyRepository.save(phoneCompany);

        return phoneCompanyMapper.map(companyFromDb);
    }

    @Transactional
    @Override
    public PhoneCompanyDTO addNumber(Long companyId, PhoneNumberDTO phoneNumberDTO) {
        return null;
    }

    @Transactional
    @Override
    public void delete(PhoneCompanyDTO phoneCompany) {
        phoneCompanyRepository.delete(phoneCompanyMapper.map(phoneCompany));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        PhoneCompanyEntity phoneCompany = phoneCompanyRepository.getReferenceById(id)
                .orElseThrow(PhoneCompanyNotFoundException::new);

        phoneCompanyRepository.delete(phoneCompany);
    }
}
