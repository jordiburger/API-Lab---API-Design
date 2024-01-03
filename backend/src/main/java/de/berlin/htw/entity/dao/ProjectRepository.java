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

import de.berlin.htw.entity.dto.ProjectEntity;

/**
 * @author Jordi Burger
 */
@ApplicationScoped
@Transactional(TxType.MANDATORY)
public class ProjectRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(TxType.NEVER)
    public List<ProjectEntity> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectEntity> cq = cb.createQuery(ProjectEntity.class);
        Root<ProjectEntity> rootEntry = cq.from(ProjectEntity.class);
        CriteriaQuery<ProjectEntity> all = cq.select(rootEntry);
        TypedQuery<ProjectEntity> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Transactional(TxType.SUPPORTS)
    public ProjectEntity get(final String ProjectId) {
        return entityManager.find(ProjectEntity.class, ProjectId);
    }

    public String add(@Valid final ProjectEntity project) {
        entityManager.persist(project);
        return project.getId();
    }

    public ProjectEntity set(@Valid final ProjectEntity project) {
        return entityManager.merge(project);
    }

    public boolean remove(final String projectId) {
        return entityManager.createNamedQuery("ProjectEntity.deleteById")
                .setParameter("id", projectId)
                .executeUpdate() > 0;
    }

    @Transactional(TxType.SUPPORTS)
    public EntityManager getEntityManager() {
        return entityManager;
    }

}