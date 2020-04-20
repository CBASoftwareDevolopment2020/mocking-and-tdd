package dk.cphbusiness.rest;

import banking.Customer;
import banking.RealCustomer;
import banking.data.access.DAO;
import banking.data.access.DBConnector;
import banking.data.access.IDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.cphbusiness.banking.CustomerDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class HelloRestService {
    IDAO dao = new DAO(DBConnector.getFakeConnection());
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET // This annotation indicates GET request
    @Path("/hello")
    public Response hello() {
        return Response.status(200).entity("hello").build();
    }

    @GET
    @Path("/customers")
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
        return Response.ok(gson.toJson(new CustomerDTO(customer.getCpr(), customer.getName()))).build();
    }

    @POST
    @Path("/add-customer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(String json) {
        Customer customer = gson.fromJson(json, RealCustomer.class);

        dao.addCustomer(customer.getCpr(), customer.getName(), customer.getBank().getCvr());

        return Response.ok(gson.toJson("")).build();
    }
}
