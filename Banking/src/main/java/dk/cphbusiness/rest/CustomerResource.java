package dk.cphbusiness.rest;

import banking.Customer;
import banking.data.access.DAO;
import banking.data.access.DBConnector;
import banking.data.access.IDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.cphbusiness.banking.CustomerDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomerResource {
    IDAO dao = new DAO(DBConnector.getFakeConnection());
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {

        List<CustomerDTO> customers = dao.getAllCustomers()
                .stream()
                .map(e -> new CustomerDTO(e.getCpr(), e.getName()))
                .collect(Collectors.toList());
        return Response.ok(gson.toJson(customers)).build();
    }

    @GET
    @Path("/customer/{cpr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("cpr") String cpr) {
        Customer customer = dao.getCustomer(cpr);
        if (customer == null){
            return Response.status(404).build();
        }
        return Response.ok(gson.toJson(new CustomerDTO(customer.getCpr(), customer.getName()))).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(String json) {
        JsonParser parser = new JsonParser();
        JsonObject data = (JsonObject) parser.parse(json);

        int response = dao.addCustomer(data.get("cpr").getAsString(), data.get("name").getAsString(), data.get("cvr").getAsString());
        if (response == 0) {
            return Response.notModified().build();
        }

        return Response.ok().build();
    }
}
