package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.dto.user.UserMainReturnDTO;
import com.epam.mazaliuk.phones.search.UserSearch;
import com.epam.mazaliuk.phones.service.UserService;
import com.epam.mazaliuk.phones.util.GeneratePDFReport;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/directory/users")
public class UserPDFController {

    private final UserService userService;

    @GetMapping(value = "/report", headers = "Accept=application/json")
    public ResponseEntity<InputStreamResource> generateReport() {

        List<UserMainReturnDTO> users = userService.find(new UserSearch());

        ByteArrayInputStream bis = GeneratePDFReport.usersReport(users);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=usersreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
