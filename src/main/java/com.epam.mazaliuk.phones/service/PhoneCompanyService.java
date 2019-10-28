package com.epam.mazaliuk.phones.service;

import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyCreateDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyMainReturnDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyUpdateDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberCreateDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.search.PhoneCompanySearch;

import java.util.List;

public interface PhoneCompanyService {

    PhoneCompanyMainReturnDTO find(Long id);

    PhoneCompanyMainReturnDTO save(PhoneCompanyCreateDTO companyCreateDTO);

    void deleteById(Long id);

    PhoneCompanyMainReturnDTO update(Long id, PhoneCompanyUpdateDTO companyUpdateDTO);

    List<PhoneCompanyMainReturnDTO> find(PhoneCompanySearch search, int offset, int limit);

    PhoneCompanyMainReturnDTO addNumber(Long companyId, PhoneNumberCreateDTO phoneNumberDTO);

    void removeNumber(Long companyId, Long numberId);

    List<PhoneNumberReturnDTO> findPhoneNumbers(Long companyId);
}
