package de.berlin.htw.boundary;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.berlin.htw.control.ProjectController;
import de.berlin.htw.lib.ProjectEndpoint;
import de.berlin.htw.lib.dto.ProjectJson;
import de.berlin.htw.lib.model.ProjectModel;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import de.berlin.htw.control.UserController;
import de.berlin.htw.lib.UserEndpoint;
import de.berlin.htw.lib.dto.UserJson;
import de.berlin.htw.lib.model.UserModel;

/**
 * @author Jordi Burger
 */
@Path(UserEndpoint.CONTEXT + "/" + UserEndpoint.VERSION + "/" + UserEndpoint.SERVICE)
public class ProjectResource implements ProjectEndpoint {

    @Context
    UriInfo uri;

    @Inject
    ProjectController controller;

    @Override
    public List<ProjectJson> getProjects() {
        final List<ProjectJson> projects = new ArrayList<>();
        controller.getProjects().forEach(project -> projects.add(new ProjectJson(project)));
        return projects;
    }

    @Override
    public Response createProject(final ProjectJson project) {
        final String projectId = controller.createProject(project);
        final URI location = uri.getAbsolutePathBuilder().path(projectId).build();
        return Response.created(location).build();
    }

    @Override
    public ProjectJson getProject(final String projectId) {
        try {
            final ProjectModel project = controller.getProject(projectId);
            return new ProjectJson(project);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid user id", e);
        }
    }

    @Override
    public ProjectJson updateProject(final String projectId, final ProjectJson project) {
        if (project.getId() != null) {
            throw new BadRequestException("User ID should not be set in payload");
        } else {
            project.setId(projectId);
        }
        final ProjectModel updatedProject = controller.updateProject(project);
        return new ProjectJson(updatedProject);
    }

    @Override
    public void deleteProject(final String projectId) {
        if (!controller.deleteProject(projectId)) {
            throw new NotFoundException();
        }
    }

}