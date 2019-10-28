package com.epam.mazaliuk.phones.mapper;

import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyReturnDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReferenceDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public final class PhoneNumberMapper {

    public PhoneNumberEntity map(PhoneNumberReferenceDTO phoneNumberDTO) {

        PhoneNumberEntity phoneNumber = new PhoneNumberEntity();
        phoneNumber.setId(phoneNumberDTO.getId());
        phoneNumber.setNumber(phoneNumberDTO.getNumber());

        return phoneNumber;
    }

    public PhoneNumberEntity map(PhoneNumberReturnDTO phoneNumberDTO) {

        PhoneNumberEntity phoneNumber = new PhoneNumberEntity();
        phoneNumber.setId(phoneNumberDTO.getId());
        phoneNumber.setNumber(phoneNumberDTO.getNumber());

        PhoneCompanyEntity companyEntity = new PhoneCompanyEntity();
        PhoneCompanyReturnDTO phoneCompanyDTO = phoneNumberDTO.getCompanyDTO();
        companyEntity.setId(phoneCompanyDTO.getId());
        companyEntity.setName(phoneCompanyDTO.getName());
        companyEntity.setYearOfIssue(phoneCompanyDTO.getYearOfIssue());
        phoneNumber.setPhoneCompany(companyEntity);

        return phoneNumber;
    }

    public PhoneNumberReferenceDTO mapToReference(PhoneNumberEntity phoneNumberEntity) {
        PhoneNumberReferenceDTO phoneNumber = new PhoneNumberReferenceDTO();
        phoneNumber.setId(phoneNumberEntity.getId());
        phoneNumber.setNumber(phoneNumberEntity.getNumber());

        return phoneNumber;
    }

    public PhoneNumberReturnDTO map(PhoneNumberEntity phoneNumberEntity) {

        PhoneNumberReturnDTO phoneNumber = new PhoneNumberReturnDTO();
        phoneNumber.setId(phoneNumberEntity.getId());
        phoneNumber.setNumber(phoneNumberEntity.getNumber());

        PhoneCompanyReturnDTO companyReturnDTO = new PhoneCompanyReturnDTO();
        PhoneCompanyEntity phoneCompanyEntity = phoneNumberEntity.getPhoneCompany();
        companyReturnDTO.setId(phoneCompanyEntity.getId());
        companyReturnDTO.setName(phoneCompanyEntity.getName());
        companyReturnDTO.setYearOfIssue(phoneCompanyEntity.getYearOfIssue());
        phoneNumber.setCompanyDTO(companyReturnDTO);

        return phoneNumber;
    }

    public List<PhoneNumberReturnDTO> mapListEntityToDTO(List<PhoneNumberEntity> phoneNumbers) {

        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return null;
        }

        return phoneNumbers.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<PhoneNumberReferenceDTO> mapListEntityToReferenceDTO(List<PhoneNumberEntity> phoneNumbers) {

        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return null;
        }

        return phoneNumbers.stream()
                .map(this::mapToReference)
                .collect(Collectors.toList());
    }


    public List<PhoneNumberEntity> mapListReferenceDTOToEntity(List<PhoneNumberReferenceDTO> phoneNumbers) {

        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return null;
        }

        return phoneNumbers.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
