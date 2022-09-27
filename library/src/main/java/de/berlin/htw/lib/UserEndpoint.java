package de.berlin.htw.lib;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.berlin.htw.lib.dto.UserJson;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Path(UserEndpoint.CONTEXT + "/" + UserEndpoint.VERSION + "/" + UserEndpoint.SERVICE)
public interface UserEndpoint {

    final static String CONTEXT = "api";
    final static String VERSION = "v2";
    final static String SERVICE = "users";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new user.")
    Response createUser(
        @Parameter(description = "User information", required = true) @Valid UserJson user);

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of a user.")
    UserJson getUser(
        @Parameter(description = "ID of the user", required = true) @PathParam("userId") String userId);

    @PATCH
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update information of a user.")
    UserJson updateUser(
        @Parameter(description = "ID of the user", required = true) @PathParam("userId") String userId,
        @Parameter(description = "User information", required = true) @Valid UserJson user);

    @DELETE
    @Path("/{userId}")
    @Operation(summary = "Delete an existing user.",
        responses = {
            @ApiResponse(responseCode = "204", description = "The user was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "The user does not exist")
        })
    void deleteUser(
        @Parameter(description = "ID of the user", required = true) @PathParam("userId") String userId);

}
