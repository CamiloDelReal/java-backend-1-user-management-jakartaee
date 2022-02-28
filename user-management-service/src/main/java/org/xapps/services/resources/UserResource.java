package org.xapps.services.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.xapps.services.dtos.UserRequest;
import org.xapps.services.dtos.UserResponse;
import org.xapps.services.exceptions.DuplicityException;
import org.xapps.services.exceptions.NotFoundException;
import org.xapps.services.services.UserService;

import java.util.List;

@Path("/users")
@RequestScoped
@Slf4j
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        log.debug("getAllUsers");
        Response response = null;
        try {
            List<UserResponse> users = userService.getAll();
            response = Response.ok(users).build();
        } catch (Exception ex) {
            log.error("Exception captured", ex);
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        log.debug("getUserById " + id);
        Response response = null;
        try {
            UserResponse user = userService.getById(id);
            response = Response.ok(user).build();
        } catch (NotFoundException ex) {
            log.debug("User with Id " + id + " not found");
            response = Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        } catch (Exception ex) {
            log.error("Exception captured", ex);
            response = Response.serverError().build();
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserRequest userRequest) {
        log.debug("createUser " + userRequest);
        Response response = null;
        try {
            UserResponse user = userService.create(userRequest);
            response = Response.ok(user).build();
        } catch (DuplicityException ex) {
            log.warn("Field with unique data duplication found: " + ex.getMessage());
            response = Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        } catch (Exception ex) {
            log.error("Exception captured", ex);
            response = Response.serverError().build();
        }
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("id") Long id, @Valid UserRequest userRequest) {
        log.debug("editUser " + id + " " + userRequest);
        Response response = null;
        try {
            UserResponse user = userService.edit(id, userRequest);
            response = Response.ok(user).build();
        } catch (DuplicityException ex) {
            log.warn("Field with unique data duplication found: " + ex.getMessage());
            response = Response.status(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage()).build();
        } catch (NotFoundException ex) {
            log.debug("User with Id " + id + " not found");
            response = Response.status(Response.Status.NOT_FOUND.getStatusCode(), ex.getMessage()).build();
        } catch (Exception ex) {
            log.error("Exception captured", ex);
            response = Response.serverError().build();
        }
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        log.debug("deleteUser " + id);
        Response response = null;
        try {
            userService.delete(id);
            response = Response.ok().build();
        } catch (Exception ex) {
            log.error("Exception captured", ex);
            response = Response.serverError().build();
        }
        return response;
    }
}
