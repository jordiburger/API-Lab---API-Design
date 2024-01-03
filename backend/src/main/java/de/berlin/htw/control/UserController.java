package de.berlin.htw.control;

import java.util.List;
import java.util.UUID;

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
import de.berlin.htw.lib.model.UserModel;


@RequestScoped
public class UserController {
    
    @Inject
    Logger logger;

    @Inject
    UserRepository repository;
    
    @Inject
    UserTransaction transaction;

    public List<? extends UserModel> getUsers() {
        return repository.getAll();
    }

    public String createUser(final UserModel user) {
        logger.infov("Creating a new user (name={0}, email={1})", user.getName(), user.getEmail());
        final UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        try {
            transaction.begin();
            String userId = repository.add(entity);
            transaction.commit();
            return userId;
        } catch (EntityExistsException e) {
            throw new AlreadyExistsException("Unable to create user", e);
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception ex) {
                throw new InternalServerErrorException("Unable to rollback on create user", ex);
            }
            throw new InternalServerErrorException("Unable to create user", e);
        }
    }

    public UserModel getUser(final String userId) {
        logger.infov("Retrieving an existing user (id = {0})", userId);
        final UserModel user = repository.get(userId);
        if(user == null) {
            throw new NotFoundException("User not exist");
        }
        return user;
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
