package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.dto.PhoneCompanyDTO;
import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.search.PhoneCompanySearch;
import com.epam.mazaliuk.phones.service.PhoneCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/companies")
public class PhoneCompanyController extends BaseController<PhoneCompanyDTO, PhoneCompanySearch> {

    private final PhoneCompanyService phoneCompanyService;

    @GetMapping
    @Override
    public List<PhoneCompanyDTO> findAll(PhoneCompanySearch search, Integer offset, Integer limit) {

        offset = validateOffset(offset);
        limit = validateLimit(limit);

        return phoneCompanyService.find(search, offset, limit);
    }

    @GetMapping("/{companyId}")
    public PhoneCompanyDTO findCompany(@PathVariable("companyId") Long companyId) {

        return phoneCompanyService.find(companyId);
    }

    @GetMapping("/{companyId}/phoneNumbers")
    public List<PhoneNumberDTO> findPhoneNumbers(@PathVariable("companyId") Long companyId) {

        return phoneCompanyService.findPhoneNumbers(companyId);
    }

    @PostMapping
    public PhoneCompanyDTO addCompany(@RequestBody PhoneCompanyDTO companyDTO) {

        return phoneCompanyService.save(companyDTO);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable("companyId") Long companyId) {

        phoneCompanyService.deleteById(companyId);
    }
}
