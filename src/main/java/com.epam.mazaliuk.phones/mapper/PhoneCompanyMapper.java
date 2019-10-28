package com.epam.mazaliuk.phones.mapper;

import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyCreateDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyMainReturnDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyReturnDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberCreateDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public final class PhoneCompanyMapper {

    private final PhoneNumberMapper phoneNumberMapper;

    public PhoneCompanyMainReturnDTO map(PhoneCompanyEntity phoneCompanyEntity) {
        PhoneCompanyMainReturnDTO phoneCompanyDTO = new PhoneCompanyMainReturnDTO();
        phoneCompanyDTO.setId(phoneCompanyEntity.getId());
        phoneCompanyDTO.setName(phoneCompanyEntity.getName());
        phoneCompanyDTO.setYearOfIssue(phoneCompanyEntity.getYearOfIssue());
        phoneCompanyDTO.setPhoneNumbers(phoneNumberMapper.mapListEntityToReferenceDTO(phoneCompanyEntity.getPhoneNumbers()));

        return phoneCompanyDTO;
    }

    public PhoneCompanyReturnDTO mapToReturn(PhoneCompanyEntity phoneCompanyEntity) {
        PhoneCompanyReturnDTO phoneCompanyDTO = new PhoneCompanyReturnDTO();
        phoneCompanyDTO.setId(phoneCompanyEntity.getId());
        phoneCompanyDTO.setName(phoneCompanyEntity.getName());
        phoneCompanyDTO.setYearOfIssue(phoneCompanyEntity.getYearOfIssue());

        return phoneCompanyDTO;
    }

    public PhoneCompanyEntity map(PhoneCompanyCreateDTO phoneCompanyDTO) {
        PhoneCompanyEntity phoneCompanyEntity = new PhoneCompanyEntity();
        phoneCompanyEntity.setName(phoneCompanyDTO.getName());
        phoneCompanyEntity.setYearOfIssue(phoneCompanyDTO.getYearOfIssue());
        phoneCompanyEntity.setPhoneNumbers(new ArrayList<>());

        List<String> numbers = phoneCompanyDTO.getPhoneNumbers().stream()
                .map(PhoneNumberCreateDTO::getNumber)
                .collect(Collectors.toList());

        numbers.forEach(number -> {
            PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
            phoneNumberEntity.setNumber(number);
            phoneCompanyEntity.addNumber(phoneNumberEntity);
        });

        return phoneCompanyEntity;
    }


    public List<PhoneCompanyMainReturnDTO> mapListEntityToDTO(List<PhoneCompanyEntity> companyEntities) {

        if (CollectionUtils.isEmpty(companyEntities)) {
            return null;
        }

        return companyEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<PhoneCompanyReturnDTO> mapListEntityToReturnDTO(List<PhoneCompanyEntity> companyEntities) {

        if (CollectionUtils.isEmpty(companyEntities)) {
            return null;
        }

        return companyEntities.stream()
                .map(this::mapToReturn)
                .collect(Collectors.toList());
    }

    public List<PhoneCompanyEntity> mapListDTOToEntity(List<PhoneCompanyCreateDTO> companyDTO) {

        if (CollectionUtils.isEmpty(companyDTO)) {
            return null;
        }

        return companyDTO.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
