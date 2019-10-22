package com.epam.mazaliuk.phones.mapper;

import com.epam.mazaliuk.phones.dto.PhoneCompanyDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public final class PhoneCompanyMapper {

    private final PhoneNumberMapper phoneNumberMapper;

    public PhoneCompanyDTO map(PhoneCompanyEntity phoneCompanyEntity) {
        PhoneCompanyDTO phoneCompanyDTO = new PhoneCompanyDTO();
        phoneCompanyDTO.setId(phoneCompanyEntity.getId());
        phoneCompanyDTO.setName(phoneCompanyEntity.getName());
        phoneCompanyDTO.setYearOfIssue(phoneCompanyEntity.getYearOfIssue());
        phoneCompanyDTO.setPhoneNumbers(phoneNumberMapper.mapListEntityToDTO(phoneCompanyEntity.getPhoneNumbers()));

        return phoneCompanyDTO;
    }

    public PhoneCompanyEntity map(PhoneCompanyDTO phoneCompanyDTO) {
        PhoneCompanyEntity phoneCompanyEntity = new PhoneCompanyEntity();
        phoneCompanyEntity.setId(phoneCompanyDTO.getId());
        phoneCompanyEntity.setName(phoneCompanyDTO.getName());
        phoneCompanyEntity.setYearOfIssue(phoneCompanyDTO.getYearOfIssue());
        phoneCompanyEntity.setPhoneNumbers(phoneNumberMapper.mapListDTOToEntity(phoneCompanyDTO.getPhoneNumbers()));

        return phoneCompanyEntity;
    }

    public List<PhoneCompanyDTO> mapListEntityToDTO(List<PhoneCompanyEntity> companyEntities) {

        if (CollectionUtils.isEmpty(companyEntities)) {
            return null;
        }

        return companyEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<PhoneCompanyEntity> mapListDTOToEntity(List<PhoneCompanyDTO> companyDTOS) {

        if (CollectionUtils.isEmpty(companyDTOS)) {
            return null;
        }

        return companyDTOS.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
