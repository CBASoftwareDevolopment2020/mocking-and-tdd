package dk.cphbusiness.rest;

import banking.Bank;
import banking.data.access.DAO;
import banking.data.access.DBConnector;
import banking.data.access.IDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.cphbusiness.banking.BankDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/banks")
public class BankResource {
    IDAO dao = new DAO(DBConnector.getFakeConnection());
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBanks() {

        List<BankDTO> banks = dao.getAllBanks()
                .stream()
                .map(e -> new BankDTO(e.getCvr(), e.getName()))
                .collect(Collectors.toList());
        return Response.ok(gson.toJson(banks)).build();
    }

    @GET
    @Path("/bank/{cvr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBank(@PathParam("cvr") String cvr) {
        Bank bank = dao.getBank(cvr);
        return Response.ok(gson.toJson(new BankDTO(bank.getCvr(), bank.getName()))).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBank(String json) {
        JsonParser parser = new JsonParser();
        JsonObject data = (JsonObject) parser.parse(json);

        int response = dao.addBank(data.get("cvr").getAsString(), data.get("name").getAsString());
        if (response == 0) {
            return Response.notModified().build();
        }

        return Response.ok().build();
    }
}
