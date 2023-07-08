package be.walbert.API;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.walbert.Javabeans.Users_API;

@Path("/users")
public class UsersAPI {

	@Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsers(@FormParam("pseudo") String pseudo, @FormParam("password") String password) {

        if (pseudo == null || password == null) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("{\"error\":\"Please, enter a pseudo and a password\"}")
                    .build();
        }

        Users_API user = Users_API.login(pseudo, password);

        if (user == null) {
            return Response.status(Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Pseudo or password incorrect.\"}")
                    .build();
        }

        return Response.status(Status.OK).entity(user).build();
    }
}