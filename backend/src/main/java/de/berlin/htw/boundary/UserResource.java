package de.berlin.htw.boundary;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.berlin.htw.control.UserController;
import de.berlin.htw.lib.UserEndpoint;
import de.berlin.htw.lib.dto.UserJson;
import de.berlin.htw.lib.model.UserModel;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
public class UserResource implements UserEndpoint {
    
    @Context
    private UriInfo uri;

    @Inject
    private UserController controller;

    @Override
    public Response createUser(@Valid final UserJson user) {
        final String userId = controller.createUser(user);
        final URI location = uri.getAbsolutePathBuilder().path(userId).build();
        return Response.created(location).build();
    }

    @Override
    public UserJson getUser(final String userId) {
        final UserModel user = controller.getUser(userId);
        return new UserJson(user);
    }

    @Override
    public UserJson updateUser(final String userId, @Valid final UserJson user) {
        if(user.getId() != null) {
            throw new BadRequestException("User ID should not be set in payload");
        } else {
            user.setId(userId);
        }
        final UserModel updatedUser = controller.updateUser(user);
        return new UserJson(updatedUser);
    }

    @Override
    public void deleteUser(final String userId) {
        if(!controller.deleteUser(userId)) {
            throw new NotFoundException();
        }
    }

}