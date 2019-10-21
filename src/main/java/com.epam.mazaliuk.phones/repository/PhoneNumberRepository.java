package com.epam.mazaliuk.phones.repository;

import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;

public abstract class PhoneNumberRepository extends AbstractRepository<PhoneNumberEntity, Long> {

    @Override
    Class<PhoneNumberEntity> getType() {
        return PhoneNumberEntity.class;
    }

}
