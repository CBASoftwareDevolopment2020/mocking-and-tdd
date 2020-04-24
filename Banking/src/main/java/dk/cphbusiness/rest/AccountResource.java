package dk.cphbusiness.rest;

import banking.Account;
import banking.data.access.DAO;
import banking.data.access.DBConnector;
import banking.data.access.IDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.cphbusiness.banking.AccountDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class AccountResource {
    IDAO dao = new DAO(DBConnector.getFakeConnection());
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts() {

        List<AccountDTO> accounts = dao.getAllAccounts()
                .stream()
                .map(e -> new AccountDTO(e.getNumber(), e.getCustomer().getCpr(), e.getBank().getCvr()))
                .collect(Collectors.toList());
        return Response.ok(gson.toJson(accounts)).build();
    }

    @GET
    @Path("/account/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("id") String id) {
        Account account = dao.getAccount(id);
        return Response.ok(gson.toJson(new AccountDTO(account.getNumber(), account.getCustomer().getCpr(), account.getBank().getCvr()))).build();
    }

    @POST
    @Path("/add-account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(String json) {
        JsonParser parser = new JsonParser();
        JsonObject data = (JsonObject) parser.parse(json);

        int response = dao.addAccount(data.get("number").getAsString(), data.get("cpr").getAsString());
        if (response == 0) {
            return Response.notModified().build();
        }

        return Response.ok(gson.toJson("{\"status\":\"Success\"}")).build();
    }

    @GET
    @Path("/accounts/{cpr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountsFromCustomer(@PathParam("cpr") String cpr){
        List<AccountDTO> accounts = dao.getAccountsFromCustomer(cpr)
                .stream()
                .map(e -> new AccountDTO(e.getNumber(), e.getCustomer().getCpr(), e.getBank().getCvr()))
                .collect(Collectors.toList());
        return Response.ok(gson.toJson(accounts)).build();
    }
}