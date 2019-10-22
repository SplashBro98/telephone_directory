package com.epam.mazaliuk.phones.mapper;

import com.epam.mazaliuk.phones.dto.UserDTO;
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

    public UserDTO map(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setCity(userEntity.getCity());
        userDTO.setPhoneNumbers(phoneNumberMapper.mapListEntityToDTO(userEntity.getPhoneNumbers()));

        return userDTO;
    }

    public UserEntity map(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setCity(userDTO.getCity());
        userEntity.setPhoneNumbers(phoneNumberMapper.mapListDTOToEntity(userDTO.getPhoneNumbers()));

        return userEntity;
    }

    public List<UserDTO> mapListEntityToDTO(List<UserEntity> users) {

        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        return users.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<UserEntity> mapListDTOToEntity(List<UserDTO> users) {

        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        return users.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
