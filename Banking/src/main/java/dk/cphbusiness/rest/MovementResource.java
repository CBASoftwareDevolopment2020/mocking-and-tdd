package dk.cphbusiness.rest;

import banking.data.access.DAO;
import banking.data.access.DBConnector;
import banking.data.access.IDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dk.cphbusiness.banking.MovementDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/movements")
public class MovementResource {
    IDAO dao = new DAO(DBConnector.getFakeConnection());
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/deposits/{acc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeposits(@PathParam("acc") String acc) {

        List<MovementDTO> deposits = dao.getDeposits(acc)
                .stream()
                .map(e -> new MovementDTO(e.getTime(), e.getAmount(), e.getSource().getNumber(), e.getTarget().getNumber()))
                .collect(Collectors.toList());
        return Response.ok(gson.toJson(deposits)).build();
    }

    @GET
    @Path("/withdrawals/{acc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWithdrawals(@PathParam("acc") String acc) {

        List<MovementDTO> withdrawals = dao.getWithdrawals(acc)
                .stream()
                .map(e -> new MovementDTO(e.getTime(), e.getAmount(), e.getSource().getNumber(), e.getTarget().getNumber()))
                .collect(Collectors.toList());
        return Response.ok(gson.toJson(withdrawals)).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovement(String json) {
        JsonParser parser = new JsonParser();
        JsonObject data = (JsonObject) parser.parse(json);

        int response = dao.addMovement(data.get("amount").getAsLong(), data.get("source_number").getAsString(), data.get("target_number").getAsString());
        if (response == 0) {
            return Response.notModified().build();
        }

        return Response.ok().build();
    }
}
