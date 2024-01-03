package de.berlin.htw.lib;

import java.util.List;

import de.berlin.htw.lib.dto.ProjectJson;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import de.berlin.htw.lib.dto.UserJson;

/**
 * @author Jordi Burger
 */
@Path(UserEndpoint.CONTEXT + "/" + UserEndpoint.VERSION + "/" + UserEndpoint.SERVICE)
public interface ProjectEndpoint {

    final static String CONTEXT = "api";
    final static String VERSION = "v2";
    final static String SERVICE = "projects";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of all projects.")
    List<ProjectJson> getProjects();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new project.")
    @APIResponse(responseCode = "201", description = "Project created successfully",
            headers = @Header(name = "Location", description = "URL to retrieve all orders"))
    @APIResponse(responseCode = "400", description = "Invalid request message")
    @APIResponse(responseCode = "409", description = "Another user with the same email already exist")
    Response createProject(
            @Parameter(description = "Project information", required = true) @Valid ProjectJson projectJson);

    @GET
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of a project.")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    ProjectJson getProject(
            @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId);

    @PATCH
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update information of a project.")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    ProjectJson updateProject(
            @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "Project information", required = true) @Valid ProjectJson project);

    @DELETE
    @Path("/{projectId}")
    @Operation(summary = "Delete an existing project.")
    @APIResponse(responseCode = "204", description = "The project was deleted successfully")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    void deleteProject(
            @Parameter(description = "ID of the user", required = true) @PathParam("projectId") String userId);

}
