package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReferenceDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserCreateDTO;
import com.epam.mazaliuk.phones.dto.user.UserMainReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserUpdateDTO;
import com.epam.mazaliuk.phones.search.UserSearch;
import com.epam.mazaliuk.phones.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/directory/users")
public class UserController extends BaseController<UserSearch> {

    private final UserService userService;

    @GetMapping
    @Override
    public ModelAndView findAll(UserSearch search, Integer offset, Integer limit) {

        offset = validateOffset(offset);
        limit = validateLimit(limit);

        List<UserMainReturnDTO> users = userService.find(search, offset, limit);

        Map<String, List<UserMainReturnDTO>> map = new HashMap<>();
        map.put("users", users);

        return new ModelAndView("showUsers", map);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView uploadUsers(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<UserCreateDTO>> typeReference = new TypeReference<List<UserCreateDTO>>() {
        };
        InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());

        List<UserCreateDTO> users = mapper.readValue(inputStream, typeReference);

        users.forEach(userService::save);

        return new ModelAndView("showUsers");
    }

    @GetMapping("/{userId}")
    public UserMainReturnDTO findUser(@PathVariable("userId") Long userId) {

        return userService.find(userId);
    }

    @GetMapping("/{userId}/phoneNumbers")
    public List<PhoneNumberReturnDTO> findPhoneNumbers(@PathVariable("userId") Long userId) {

        return userService.findPhoneNumbers(userId);
    }

    @PostMapping
    public UserMainReturnDTO addUser(@RequestBody UserCreateDTO userDTO) {

        return userService.save(userDTO);
    }

    @PostMapping("/{userId}/phoneNumber")
    public UserMainReturnDTO addNumber(@PathVariable("userId") Long userId, @RequestBody PhoneNumberReferenceDTO phoneNumberDTO) {

        return userService.addNumber(userId, phoneNumberDTO);
    }

    @PutMapping("/{userId}")
    public UserMainReturnDTO updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO userDTO) {

        return userService.update(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {

        userService.deleteById(id);
    }

    @DeleteMapping("/{userId}/phoneNumbers/{numberId}")
    public void removeNumber(@PathVariable("userId") Long userId, @PathVariable("numberId") Long numberId) {

        userService.removeNumber(userId, numberId);
    }
}
