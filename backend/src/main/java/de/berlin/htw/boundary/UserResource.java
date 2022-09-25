package de.berlin.htw.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.berlin.htw.boundary.dto.Orders;

@Path("/users/{id}")
public class UserResource {

    @GET
    @Path("/orders")
    @Produces("application/json")
    public Orders filterOrdersByName(
            @PathParam("id") String userId, 
            @QueryParam("name") String name) {
        
        return getOrdersByName(userId, name);
    }

    private Orders getOrdersByName(String userId, String name) {
        // TODO Auto-generated method stub
        return null;
    }
}