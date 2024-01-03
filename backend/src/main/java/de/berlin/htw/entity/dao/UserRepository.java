package de.berlin.htw.entity.dao;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.validation.Valid;

import de.berlin.htw.entity.dto.UserEntity;


@ApplicationScoped
@Transactional(TxType.MANDATORY)
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional(TxType.NEVER)
    public List<UserEntity> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        CriteriaQuery<UserEntity> all = cq.select(rootEntry);
        TypedQuery<UserEntity> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Transactional(TxType.SUPPORTS)
    public UserEntity get(final String userId) {
        return entityManager.find(UserEntity.class, userId);
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