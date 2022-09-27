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
    public UserEntity get(final String userId) {
        return entityManager.find(UserEntity.class, userId);
    }

    public String add(final UserEntity user) {
        entityManager.persist(user);
        return user.getId();
    }

    public UserEntity set(final UserEntity user) {
        return entityManager.merge(user);
    }

    public boolean remove(final String userId) {
        return entityManager.createNamedQuery("UserEntity.deleteById")
            .setParameter("id", userId)
            .executeUpdate() > 0;
    }
    
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

}