package de.berlin.htw.control;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;

import org.jboss.logging.Logger;

import de.berlin.htw.boundary.AlreadyExistsException;
import de.berlin.htw.entity.dao.UserRepository;
import de.berlin.htw.entity.dto.UserEntity;
import de.berlin.htw.lib.model.UserModel;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@RequestScoped
public class UserController {
    
    @Inject
    Logger logger;

    @Inject
    UserRepository repository;

    public String createUser(final UserModel user) {
        logger.infov("Creating a new user ({0})", user);
        final UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        try {
            repository.beginTransaction();
            String userId = repository.add(entity);
            repository.commitTransaction();
            return userId;
        } catch (EntityExistsException e) {
            throw new AlreadyExistsException("Unable to create user", e);
        } catch (Exception e) {
            repository.rollbackTransaction();
            throw new InternalServerErrorException("Unable to create user", e);
        }
    }

    public UserModel getUser(final String userId) {
        logger.infov("Retrieving an existing user (id = {0})", userId);
        return repository.get(userId);
    }

    @Transactional
    public UserModel updateUser(final UserModel user) {
        logger.infov("Updating an existing user ({0})", user);
        final UserEntity entity = repository.get(user.getId());
        if(user.getName() != null) {
            entity.setName(user.getName());
        }
        if(user.getEmail() != null) {
            entity.setEmail(user.getEmail());
        }
        return repository.set(entity);
    }

    @Transactional
    public boolean deleteUser(final String userId) {
        logger.infov("Deleting an existing user (id = {0})", userId);
        return repository.remove(userId);
    }

}
