package de.berlin.htw.entity;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.inject.Inject;
import jakarta.transaction.Status;
import jakarta.transaction.TransactionalException;
import jakarta.transaction.UserTransaction;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.berlin.htw.AbstractTest;
import de.berlin.htw.entity.dao.UserRepository;
import de.berlin.htw.entity.dto.UserEntity;

@QuarkusTest
class UserRepositoryTest extends AbstractTest {
    
    static final String ID = "testID";
    static final String NAME = "Max Mustermann";
    static final String EMAIL = "max.mustermann@example.org";

    @Inject
    UserRepository repository;
    
    @Inject
    UserTransaction transaction;

    @AfterEach
    void cleanUp() throws Exception {
        if (transaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
            transaction.rollback();
        }
    }

    @Test
    void testTransactionRequired() {
        assertThrows(
            TransactionalException.class,
            () -> repository.add(new UserEntity()));
    }

    @Test
    void testAddAndGet() throws Exception {
        final UserEntity entity = new UserEntity();
        entity.setId(ID);
        entity.setName(NAME);
        entity.setEmail(EMAIL);

        transaction.begin();
        final String userId = repository.add(entity);
        assertNotNull(userId);
        assertEquals(ID, userId);
        transaction.commit();
        repository.getEntityManager().clear();

        assertEquals(ID, repository.get(userId).getId());
        assertEquals(NAME, repository.get(userId).getName());
        assertEquals(EMAIL, repository.get(userId).getEmail());
    }

    @Test
    void testValidationOnAdd() throws Exception {
        final UserEntity entity = new UserEntity();
        entity.setId(ID);
        entity.setName("invalidE-Mail");
        entity.setEmail(NAME);

        transaction.begin();
        assertThrows(
            ConstraintViolationException.class,
            () -> repository.add(entity));
        transaction.rollback();
    }

}