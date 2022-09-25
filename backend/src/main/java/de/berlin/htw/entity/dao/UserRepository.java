package de.berlin.htw.entity.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import de.berlin.htw.entity.dto.UserEntity;

@Transactional(TxType.MANDATORY)
public class UserRepository {

    @Inject
    private EntityManager entityManager;
    
    @Transactional(TxType.SUPPORTS)
    UserEntity get(final String userId) {
        return entityManager.find(UserEntity.class, userId);
    }

    void add(final UserEntity user) {
        entityManager.persist(user);
    }

}