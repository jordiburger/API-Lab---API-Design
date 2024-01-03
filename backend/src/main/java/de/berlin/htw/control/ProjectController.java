package de.berlin.htw.control;

import java.util.List;
import java.util.UUID;

import de.berlin.htw.entity.dao.ProjectRepository;
import de.berlin.htw.entity.dto.ProjectEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;

import org.jboss.logging.Logger;

import de.berlin.htw.boundary.AlreadyExistsException;
import de.berlin.htw.entity.dao.UserRepository;
import de.berlin.htw.entity.dto.UserEntity;
import de.berlin.htw.lib.model.ProjectModel;

/**
 * @author Jordi Burger
 */
@RequestScoped
public class ProjectController {

    @Inject
    Logger logger;

    @Inject
    ProjectRepository repository;

    @Inject
    UserTransaction transaction;

    public List<? extends ProjectModel> getProjects() {
        return repository.getAll();
    }

    public String createProject(final ProjectModel user) {
        logger.infov("Creating a new project (name={0}, description={1})", user.getTitle(), user.getDescription());
        final ProjectEntity entity = new ProjectEntity();
        entity.setId(UUID.randomUUID());
        entity.setTitle(user.getTitle());
        entity.setDescription(user.getDescription());
        try {
            transaction.begin(); // Vorgabe aus dem Project Repository
            String userId = repository.add(entity);
            transaction.commit();
            return userId;
        } catch (EntityExistsException e) {
            throw new AlreadyExistsException("Unable to create project", e);
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception ex) {
                throw new InternalServerErrorException("Unable to rollback on create project", ex);
            }
            throw new InternalServerErrorException("Unable to create project", e);
        }
    }

    public ProjectModel getProject(final String projectId) {
        logger.infov("Retrieving an existing project (id = {0})", projectId);
        final ProjectModel project = repository.get(projectId);
        if(project == null) {
            throw new NotFoundException("Project not exist");
        }
        return project;
    }

    @Transactional
    public ProjectModel updateProject(final ProjectModel project) {
        logger.infov("Updating an existing project ({0})", project);
        final ProjectEntity entity = repository.get(project.getId());
        if(project.getTitle() != null) {
            entity.setTitle(project.getTitle());
        }
        if(project.getDescription() != null) {
            entity.setDescription(project.getDescription());
        }
        return repository.set(entity);
    }

    @Transactional
    public boolean deleteProject(final String projectId) {
        logger.infov("Deleting an existing project (id = {0})", projectId);
        return repository.remove(projectId);
    }

}
