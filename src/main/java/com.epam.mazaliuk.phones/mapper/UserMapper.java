package com.epam.mazaliuk.phones.mapper;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserCreateDTO;
import com.epam.mazaliuk.phones.dto.user.UserMainReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserReferenceDTO;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.entity.UserEntity;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public final class UserMapper {

    private final PhoneNumberMapper phoneNumberMapper;

    public UserMainReturnDTO map(UserEntity userEntity) {
        UserMainReturnDTO userDTO = new UserMainReturnDTO();
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setCity(userEntity.getCity());

        if (CollectionUtils.isNotEmpty(userEntity.getPhoneNumbers())) {

            List<PhoneNumberReturnDTO> phoneNumbers = userEntity.getPhoneNumbers().stream()
                    .map(phoneNumberMapper::map)
                    .collect(Collectors.toList());

            userDTO.setPhoneNumbers(phoneNumbers);
        }

        return userDTO;
    }

    public UserReferenceDTO mapToReference(UserEntity userEntity) {

        UserReferenceDTO userDTO = new UserReferenceDTO();
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setCity(userEntity.getCity());

        return userDTO;
    }

    public UserEntity map(UserCreateDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setCity(userDTO.getCity());

        if (CollectionUtils.isNotEmpty(userDTO.getPhoneNumbers())) {

            List<PhoneNumberEntity> phoneNumbers = userDTO.getPhoneNumbers().stream()
                    .map(phoneNumberMapper::map)
                    .collect(Collectors.toList());
            userEntity.setPhoneNumbers(phoneNumbers);
        }

        return userEntity;
    }

    public UserEntity map(UserReferenceDTO userReferenceDTO) {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userReferenceDTO.getId());
        userEntity.setFirstName(userReferenceDTO.getFirstName());
        userEntity.setLastName(userReferenceDTO.getLastName());
        userEntity.setCity(userReferenceDTO.getCity());

        return userEntity;

    }

    public List<UserMainReturnDTO> mapListEntityToDTO(List<UserEntity> users) {

        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        return users.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<UserReferenceDTO> mapListEntityToReferenceDTO(List<UserEntity> users) {

        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        return users.stream()
                .map(this::mapToReference)
                .collect(Collectors.toList());
    }
}
