package com.epam.mazaliuk.phones.service;

import com.epam.mazaliuk.phones.dto.PhoneCompanyDTO;
import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.search.PhoneCompanySearch;

import java.util.List;

public interface PhoneCompanyService extends BaseService<PhoneCompanyDTO, PhoneCompanySearch, Long> {

    PhoneCompanyDTO addNumber(Long companyId, PhoneNumberDTO phoneNumberDTO);

    List<PhoneNumberDTO> findPhoneNumbers(Long companyId);

}
