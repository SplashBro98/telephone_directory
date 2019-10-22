package com.epam.mazaliuk.phones.service.impl;

import com.epam.mazaliuk.phones.dto.PhoneNumberDTO;
import com.epam.mazaliuk.phones.dto.UserDTO;
import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.entity.UserEntity;
import com.epam.mazaliuk.phones.exception.PhonenNumberException;
import com.epam.mazaliuk.phones.exception.UserNotFoundException;
import com.epam.mazaliuk.phones.mapper.PhoneNumberMapper;
import com.epam.mazaliuk.phones.mapper.UserMapper;
import com.epam.mazaliuk.phones.repository.PhoneCompanyRepository;
import com.epam.mazaliuk.phones.repository.PhoneNumberRepository;
import com.epam.mazaliuk.phones.repository.UserRepository;
import com.epam.mazaliuk.phones.search.UserSearch;
import com.epam.mazaliuk.phones.service.UserService;
import com.epam.mazaliuk.phones.specification.BaseSpecification;
import com.epam.mazaliuk.phones.specification.phonecompany.PhoneCompanyFindByNameSpecification;
import com.epam.mazaliuk.phones.specification.phonenumber.PhoneNumberFindByNumberSpecification;
import com.epam.mazaliuk.phones.specification.user.UserFindByCitySpecification;
import com.epam.mazaliuk.phones.specification.user.UserFindByFirstNameSpecification;
import com.epam.mazaliuk.phones.specification.user.UserFindByLastNameSpecification;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import com.epam.mazaliuk.phones.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final PhoneCompanyRepository phoneCompanyRepository;

    private final UserMapper userMapper;

    private final PhoneNumberMapper phoneNumberMapper;

    @Transactional
    @Override
    public List<UserDTO> find(UserSearch userSearch, int offset, int limit) {

        BaseSpecification<UserEntity> specification = BaseSpecification.empty();

        String firstName = userSearch.getFirstName();
        if (StringUtils.isNotEmpty(firstName)) {
            specification = specification.and(new UserFindByFirstNameSpecification(firstName));
        }

        String lastName = userSearch.getLastName();
        if (StringUtils.isNotEmpty(lastName)) {
            specification = specification.and(new UserFindByLastNameSpecification(lastName));
        }

        String city = userSearch.getCity();
        if (StringUtils.isNotEmpty(city)) {
            specification = specification.and(new UserFindByCitySpecification(city));
        }

        List<UserEntity> users = userRepository.find(specification, offset, limit);

        return userMapper.mapListEntityToDTO(users);
    }

    @Transactional
    @Override
    public UserDTO find(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        return userEntity.map(userMapper::map).orElse(null);
    }

    @Transactional
    @Override
    public List<PhoneNumberDTO> findPhoneNumbers(Long userId) {
        Optional<UserEntity> userEntity = userRepository.getReferenceById(userId);

        UserEntity user = userEntity.orElseThrow(UserNotFoundException::new);

        return phoneNumberMapper.mapListEntityToDTO(user.getPhoneNumbers());
    }

    @Transactional
    @Override
    public UserDTO save(UserDTO userDTO) {

        UserEntity userEntity = userMapper.map(userDTO);
        List<PhoneNumberEntity> phoneNumbers = userEntity.getPhoneNumbers();

        if (!CollectionUtils.isEmpty(phoneNumbers)) {

            userEntity.setPhoneNumbers(new ArrayList<>());

            phoneNumbers.forEach(number -> {

                PhoneNumberEntity phoneNumber = phoneNumberRepository
                        .findSingle(new PhoneNumberFindByNumberSpecification(number.getNumber()))
                        .orElse(number);

                PhoneCompanyEntity company = number.getPhoneCompany();
                if (company != null) {
                    PhoneCompanyEntity phoneCompany = phoneCompanyRepository
                            .findSingle(new PhoneCompanyFindByNameSpecification(company.getName()))
                            .orElse(company);
                    phoneNumber.setPhoneCompany(phoneCompany);
                }

                userEntity.addNumber(phoneNumber);
                phoneNumber.setUser(userEntity);
            });
        }

        UserEntity userFromDb = userRepository.save(userEntity);

        return userMapper.map(userFromDb);
    }

    @Transactional
    @Override
    public UserDTO addNumber(Long userId, PhoneNumberDTO phoneNumberDTO) {
        UserEntity userEntity = userRepository.getReferenceById(userId)
                .orElseThrow(UserNotFoundException::new);
        PhoneNumberEntity phoneNumber = phoneNumberMapper.map(phoneNumberDTO);
        String number = phoneNumber.getNumber();

        if (StringUtils.isEmpty(number)) {
            throw new PhonenNumberException("empty number");
        }

        phoneNumber = phoneNumberRepository.findSingle(new PhoneNumberFindByNumberSpecification(number))
                .orElse(phoneNumber);
        userEntity.addNumber(phoneNumber);
        phoneNumber.setUser(userEntity);

        return userMapper.map(userEntity);
    }

    @Transactional
    @Override
    public UserDTO update(Long userId, UserDTO userDTO) {

        UserEntity userFromDb = userRepository.getReferenceById(userId)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.map(userFromDb);
    }

    @Transactional
    @Override
    public void delete(UserDTO user) {
        userRepository.delete(userMapper.map(user));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        UserEntity user = userRepository.getReferenceById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }
}
