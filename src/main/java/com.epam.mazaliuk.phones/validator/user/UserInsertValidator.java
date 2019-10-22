package com.epam.mazaliuk.phones.validator.user;

import com.epam.mazaliuk.phones.entity.UserEntity;
import com.epam.mazaliuk.phones.exception.UserException;
import com.epam.mazaliuk.phones.repository.UserRepository;
import com.epam.mazaliuk.phones.specification.user.UserFindByUserNameSpecification;
import com.epam.mazaliuk.phones.validator.EntityValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class UserInsertValidator implements EntityValidator<UserEntity> {

    private final UserRepository userRepository;


    @Override
    public void validate(UserEntity entity) {

        validateUserName(entity);
        validateFirstName(entity);
        validateLastName(entity);
        validateCity(entity);
    }

    public void validateUserName(UserEntity entity) {

        String userName = entity.getUserName();

        if (userName.isEmpty()) {
            throw new UserException("UserName is empty");
        }

        if (userName.length() > 40) {
            throw new UserException("UserName:" + userName + " is too long");
        }

        Optional<UserEntity> userFromDb = userRepository.findSingle(new UserFindByUserNameSpecification(userName));

        if (userFromDb.isPresent()) {
            throw new UserException("UserName:" + userName + " is already exist");
        }
    }

    public void validateFirstName(UserEntity entity) {

        String firstName = entity.getFirstName();

        if (firstName.isEmpty()) {
            throw new UserException("FirstName is empty");
        }

        if (firstName.length() > 40) {
            throw new UserException("FirstName:" + firstName + " is too long");
        }
    }

    public void validateLastName(UserEntity entity) {

        String lastName = entity.getFirstName();

        if (lastName.isEmpty()) {
            throw new UserException("LastName is empty");
        }

        if (lastName.length() > 40) {
            throw new UserException("LastName:" + lastName + " is too long");
        }
    }

    public void validateCity(UserEntity entity) {

        String city = entity.getCity();

        if (city.isEmpty()) {
            throw new UserException("City is empty");
        }

        if (city.length() > 40) {
            throw new UserException("City:" + city + " is too long");
        }
    }

}
