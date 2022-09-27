package de.berlin.htw.entity.dao;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import de.berlin.htw.entity.dto.UserEntity;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@ApplicationScoped
@Transactional(TxType.MANDATORY)
public class UserRepository {

    @Inject
    EntityManager entityManager;
    
    @Transactional(TxType.SUPPORTS)
    public UserEntity get(final String userId) {
        final UUID id = UUID.fromString(userId);
        return entityManager.find(UserEntity.class, id);
    }

    public String add(@Valid final UserEntity user) {
        entityManager.persist(user);
        return user.getId();
    }

    public UserEntity set(@Valid final UserEntity user) {
        return entityManager.merge(user);
    }

    public boolean remove(final String userId) {
        return entityManager.createNamedQuery("UserEntity.deleteById")
            .setParameter("id", userId)
            .executeUpdate() > 0;
    }

    @Transactional(TxType.SUPPORTS)
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}