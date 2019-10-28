package com.epam.mazaliuk.phones.service;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReferenceDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserCreateDTO;
import com.epam.mazaliuk.phones.dto.user.UserMainReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserUpdateDTO;
import com.epam.mazaliuk.phones.search.UserSearch;

import java.util.List;

public interface UserService {

    UserMainReturnDTO find(Long id);

    UserMainReturnDTO save(UserCreateDTO userCreateDTO);

    void deleteById(Long id);

    UserMainReturnDTO update(Long id, UserUpdateDTO userUpdateDTO);

    List<UserMainReturnDTO> find(UserSearch search, int offset, int limit);

    List<UserMainReturnDTO> find(UserSearch search);

    UserMainReturnDTO addNumber(Long userId, PhoneNumberReferenceDTO phoneNumberDTO);

    void removeNumber(Long userId, Long numberId);

    List<PhoneNumberReturnDTO> findPhoneNumbers(Long userId);
}
