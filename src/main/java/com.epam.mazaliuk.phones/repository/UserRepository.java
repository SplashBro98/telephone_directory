package com.epam.mazaliuk.phones.repository;

import com.epam.mazaliuk.phones.entity.UserEntity;

public abstract class UserRepository extends AbstractRepository<UserEntity, Long> {

    @Override
    Class<UserEntity> getType() {
        return UserEntity.class;
    }

}
