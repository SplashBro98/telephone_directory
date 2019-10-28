package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyCreateDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyMainReturnDTO;
import com.epam.mazaliuk.phones.dto.phonecompany.PhoneCompanyUpdateDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberCreateDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.search.PhoneCompanySearch;
import com.epam.mazaliuk.phones.service.PhoneCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/directory/companies")
public class PhoneCompanyController extends BaseController<PhoneCompanySearch> {

    private final PhoneCompanyService phoneCompanyService;

    @GetMapping
    @Override
    public ModelAndView findAll(PhoneCompanySearch search, Integer offset, Integer limit) {

        offset = validateOffset(offset);
        limit = validateLimit(limit);

        List<PhoneCompanyMainReturnDTO> companies = phoneCompanyService.find(search, offset, limit);

        Map<String, List<PhoneCompanyMainReturnDTO>> map = new HashMap<>();
        map.put("companies", companies);

        return new ModelAndView("showCompanies", map);
    }

    @GetMapping("/{companyId}")
    public PhoneCompanyMainReturnDTO findCompany(@PathVariable("companyId") Long companyId) {

        return phoneCompanyService.find(companyId);
    }

    @GetMapping("/{companyId}/phoneNumbers")
    public List<PhoneNumberReturnDTO> findPhoneNumbers(@PathVariable("companyId") Long companyId) {

        return phoneCompanyService.findPhoneNumbers(companyId);
    }

    @PostMapping
    public PhoneCompanyMainReturnDTO addCompany(@RequestBody PhoneCompanyCreateDTO companyDTO) {

        return phoneCompanyService.save(companyDTO);
    }

    @PostMapping("/{companyId}/phoneNumber")
    public PhoneCompanyMainReturnDTO addNumber(@PathVariable("companyId") Long companyId, @RequestBody PhoneNumberCreateDTO phoneNumberDTO) {

        return phoneCompanyService.addNumber(companyId, phoneNumberDTO);
    }

    @PutMapping("/{companyId}")
    public PhoneCompanyMainReturnDTO updateCompany(@PathVariable("companyId") Long companyId, @RequestBody PhoneCompanyUpdateDTO companyDTO) {

        return phoneCompanyService.update(companyId, companyDTO);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable("companyId") Long companyId) {

        phoneCompanyService.deleteById(companyId);
    }

    @DeleteMapping("/{companyId}/phoneNumbers/{numberId}")
    public void removeNumber(@PathVariable("companyId") Long companyId, @PathVariable("numberId") Long numberId) {

        phoneCompanyService.removeNumber(companyId, numberId);
    }
}
