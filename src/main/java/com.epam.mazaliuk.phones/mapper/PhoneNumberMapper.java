package com.epam.mazaliuk.phones.mapper;

import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class PhoneNumberMapper {

    public PhoneNumberEntity map(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumberEntity phoneNumber = new PhoneNumberEntity();
        phoneNumber.setId(phoneNumberDTO.getId());
        phoneNumber.setNumber(phoneNumberDTO.getNumber());
        PhoneCompanyEntity phoneCompanyEntity = new PhoneCompanyEntity();
        phoneCompanyEntity.setName(phoneNumberDTO.getPhoneCompany());
        phoneNumber.setPhoneCompany(phoneCompanyEntity);

        return phoneNumber;
    }

    public PhoneNumberDTO map(PhoneNumberEntity phoneNumberEntity) {
        PhoneNumberDTO phoneNumber = new PhoneNumberDTO();
        phoneNumber.setId(phoneNumberEntity.getId());
        phoneNumber.setNumber(phoneNumberEntity.getNumber());
        phoneNumber.setPhoneCompany(phoneNumberEntity.getPhoneCompany().getName());

        return phoneNumber;
    }

    public List<PhoneNumberDTO> mapListEntityToDTO(List<PhoneNumberEntity> phoneNumbers) {

        if(CollectionUtils.isEmpty(phoneNumbers)) {
            return null;
        }

        return phoneNumbers.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<PhoneNumberEntity> mapListDTOToEntity(List<PhoneNumberDTO> phoneNumbers) {

        if(CollectionUtils.isEmpty(phoneNumbers)) {
            return null;
        }

        return phoneNumbers.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
