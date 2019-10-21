package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.dto.UserDTO;
import com.epam.mazaliuk.phones.search.UserSearch;
import com.epam.mazaliuk.phones.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController extends BaseController<UserDTO, UserSearch> {

    private final UserService userService;

    @GetMapping
    @Override
    public List<UserDTO> findAll(UserSearch search, Integer offset, Integer limit) {

        offset = validateOffset(offset);
        limit = validateLimit(limit);

        return userService.find(search, offset, limit);
    }

    @GetMapping("/{userId}")
    public UserDTO findUser(@PathVariable("userId") Long userId) {

        return userService.find(userId);
    }

    @GetMapping("/{userId}/phoneNumbers")
    public List<PhoneNumberDTO> findPhoneNumbers(@PathVariable("userId") Long userId, Integer offset, Integer limit) {

        return userService.findPhoneNumbers(userId);
    }


    @PostMapping
    public UserDTO addUser(@RequestBody UserDTO userDTO) {

        return userService.save(userDTO);
    }

    @PostMapping("/{userId}")
    public UserDTO addNumber(@PathVariable("userId") Long userId, @RequestBody PhoneNumberDTO phoneNumberDTO) {

        return userService.addNumber(userId, phoneNumberDTO);
    }


    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {

        userService.deleteById(id);
    }
}
