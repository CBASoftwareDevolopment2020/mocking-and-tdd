package dk.cphbusiness.rest;

import banking.data.access.DBConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;

@Path("/")
public class Resource {
    @GET
    @Path("/reset")
    public Response reset(){
        try {
            PreparedStatement stm = DBConnector.getFakeConnection().prepareStatement("CALL setup_fbanking()");
            stm.executeUpdate();
            return Response.ok().build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
