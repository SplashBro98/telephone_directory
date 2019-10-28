package com.epam.mazaliuk.phones.service.impl;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReferenceDTO;
import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserCreateDTO;
import com.epam.mazaliuk.phones.dto.user.UserMainReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserUpdateDTO;
import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
import com.epam.mazaliuk.phones.entity.UserEntity;
import com.epam.mazaliuk.phones.exception.PhoneNumberNotFoundException;
import com.epam.mazaliuk.phones.exception.PhonenNumberException;
import com.epam.mazaliuk.phones.exception.UserNotFoundException;
import com.epam.mazaliuk.phones.mapper.PhoneNumberMapper;
import com.epam.mazaliuk.phones.mapper.UserMapper;
import com.epam.mazaliuk.phones.repository.PhoneNumberRepository;
import com.epam.mazaliuk.phones.repository.UserRepository;
import com.epam.mazaliuk.phones.search.UserSearch;
import com.epam.mazaliuk.phones.service.UserService;
import com.epam.mazaliuk.phones.specification.BaseSpecification;
import com.epam.mazaliuk.phones.specification.user.UserFindByCitySpecification;
import com.epam.mazaliuk.phones.specification.user.UserFindByFirstNameSpecification;
import com.epam.mazaliuk.phones.specification.user.UserFindByLastNameSpecification;
import com.epam.mazaliuk.phones.util.CollectionUtils;
import com.epam.mazaliuk.phones.util.Const;
import com.epam.mazaliuk.phones.util.StringUtils;
import com.epam.mazaliuk.phones.validator.EntityValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final UserMapper userMapper;

    private final PhoneNumberMapper phoneNumberMapper;

    @Qualifier("userInsertValidator")
    private final EntityValidator<UserEntity> insertValidator;

    @Transactional
    @Override
    public List<UserMainReturnDTO> find(UserSearch userSearch, int offset, int limit) {

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

    @Override
    public List<UserMainReturnDTO> find(UserSearch search) {
        return find(search, Const.DEFAULT_OFFSET, Const.DEFAULT_LIMIT);
    }

    @Transactional
    @Override
    public UserMainReturnDTO find(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        return userEntity.map(userMapper::map)
                .orElseThrow(() -> new UserNotFoundException("User with provided ID not found"));
    }

    @Transactional
    @Override
    public List<PhoneNumberReturnDTO> findPhoneNumbers(Long userId) {
        UserEntity userEntity = userRepository
                .getReferenceById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with provided ID not found"));

        return phoneNumberMapper.mapListEntityToDTO(userEntity.getPhoneNumbers());
    }

    @Transactional
    @Override
    public UserMainReturnDTO save(UserCreateDTO userDTO) {

        UserEntity userEntity = userMapper.map(userDTO);

        insertValidator.validate(userEntity);

        List<PhoneNumberEntity> phoneNumbers = userEntity.getPhoneNumbers();
        if (!CollectionUtils.isEmpty(phoneNumbers)) {

            userEntity.setPhoneNumbers(new ArrayList<>());

            phoneNumbers.forEach(number -> {

                Long numberId = number.getId();
                adjustPhoneNumber(numberId, userEntity);
            });
        }

        UserEntity userFromDb = userRepository.save(userEntity);

        return userMapper.map(userFromDb);
    }

    @Transactional
    @Override
    public UserMainReturnDTO addNumber(Long userId, PhoneNumberReferenceDTO phoneNumberDTO) {
        UserEntity userEntity = userRepository
                .getReferenceById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with provided ID not found"));

        Long numberId = phoneNumberDTO.getId();
        adjustPhoneNumber(numberId, userEntity);

        return userMapper.map(userEntity);
    }

    @Transactional
    @Override
    public UserMainReturnDTO update(Long userId, UserUpdateDTO userDTO) {

        UserEntity userFromDb = userRepository.getReferenceById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with provided ID not found"));

        String userName = userDTO.getUserName();
        if (StringUtils.isNotEmpty(userName)) {
            userFromDb.setUserName(userName);
        }

        String firstName = userDTO.getFirstName();
        if (StringUtils.isNotEmpty(firstName)) {
            userFromDb.setFirstName(firstName);
        }

        String lastName = userDTO.getLastName();
        if (StringUtils.isNotEmpty(lastName)) {
            userFromDb.setLastName(lastName);
        }

        String city = userDTO.getCity();
        if (StringUtils.isNotEmpty(city)) {
            userFromDb.setCity(city);
        }

        List<PhoneNumberEntity> phoneNumbers = userFromDb.getPhoneNumbers();
        if (CollectionUtils.isNotEmpty(phoneNumbers)) {
            phoneNumbers.forEach(number -> number.setUser(null));
        }

        userFromDb.setPhoneNumbers(new ArrayList<>());
        phoneNumbers = phoneNumberMapper.mapListReferenceDTOToEntity(userDTO.getPhoneNumbers());

        if (CollectionUtils.isNotEmpty(phoneNumbers)) {
            phoneNumbers.forEach(number -> userFromDb.addNumber(phoneNumberRepository
                    .getReferenceById(number.getId())
                    .orElseThrow(() -> new PhoneNumberNotFoundException("Phone number with provided ID not found"))));
        }

        return userMapper.map(userFromDb);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        UserEntity user = userRepository
                .getReferenceById(id)
                .orElseThrow(() -> new UserNotFoundException("User with provided ID not found"));

        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void removeNumber(Long userId, Long numberId) {

        UserEntity user = userRepository.getReferenceById(userId)
                .orElseThrow(UserNotFoundException::new);

        PhoneNumberEntity phoneNumber = phoneNumberRepository
                .getReferenceById(numberId)
                .orElseThrow(() -> new PhoneNumberNotFoundException("Phone number with provided ID not found"));

        user.removeNumber(phoneNumber);
        phoneNumber.setUser(null);
    }

    private void adjustPhoneNumber(Long numberId, UserEntity entity) {

        if (numberId == null) {
            throw new PhonenNumberException("Empty id");
        }

        Optional<PhoneNumberEntity> existingNumber = phoneNumberRepository.getReferenceById(numberId);

        if (!existingNumber.isPresent()) {
            throw new PhoneNumberNotFoundException("Phone number with provided ID not found");
        }

        entity.addNumber(existingNumber.get());
        existingNumber.get().setUser(entity);
    }
}
