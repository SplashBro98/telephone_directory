package com.epam.mazaliuk.phones.repository;

import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;

public abstract class PhoneCompanyRepository extends AbstractRepository<PhoneCompanyEntity, Long> {

    @Override
    Class<PhoneCompanyEntity> getType() {
        return PhoneCompanyEntity.class;
    }

}
