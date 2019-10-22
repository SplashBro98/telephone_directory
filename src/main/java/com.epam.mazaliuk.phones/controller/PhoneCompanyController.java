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
@RequestMapping("/directory/companies")
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

    @PostMapping("/{companyId}")
    public PhoneCompanyDTO addNumber(@PathVariable("companyId") Long companyId, @RequestBody PhoneNumberDTO phoneNumberDTO) {

        return phoneCompanyService.addNumber(companyId, phoneNumberDTO);
    }

    @PutMapping("/{companyId}")
    public PhoneCompanyDTO updateCompany(@PathVariable("companyId") Long companyId, @RequestBody PhoneCompanyDTO companyDTO) {

        return phoneCompanyService.update(companyId, companyDTO);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable("companyId") Long companyId) {

        phoneCompanyService.deleteById(companyId);
    }
}
