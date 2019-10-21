package com.epam.mazaliuk.phones.service;

import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.dto.UserDTO;
import com.epam.mazaliuk.phones.search.UserSearch;

import java.util.List;

public interface UserService extends BaseService<UserDTO, UserSearch, Long> {

    UserDTO addNumber(Long userId, PhoneNumberDTO phoneNumberDTO);

    List<PhoneNumberDTO> findPhoneNumbers(Long userId);
}
