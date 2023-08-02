package be.walbert.API;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import be.walbert.Javabeans.Users_API;

@Path("/users")
public class UsersAPI {

	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsers(@FormParam("pseudo") String pseudo, @FormParam("password") String password,
			@FormParam("email") String email) {
		try {
			if(pseudo == null || password == null || email == null) {
				return Response
						.status(Status.BAD_REQUEST)
						.build();
			}
			
			Users_API user = new Users_API(0,pseudo, password, email);
			
			boolean success = user.create();
			if(!success) {
				return Response
						.status(Status.SERVICE_UNAVAILABLE)
						.build();
			}
			else {
				return Response
						.status(Status.CREATED)
						.header("Location", "/MyPresentLists_API/api/users/" + user.getId())
						.build();
			}
		} catch (Exception e) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsers(@FormParam("pseudo") String pseudo, @FormParam("password") String password) {
		try {
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
		} catch (Exception e) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
      
    }
	
	@Path("/isUsersExist")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response isUsersExist(@FormParam("pseudo") String pseudo, @FormParam("email") String email) {
		try {
			if (pseudo == null || email==null) {
	            return Response.status(Status.BAD_REQUEST)
	                    .build();
	        }

	        Users_API u = new Users_API(0,pseudo,"",email);

	        if (u.isUsersExist()==null) {
	            return Response.status(Status.UNAUTHORIZED)
	                    .build();
	        }
	        else {
	            return Response.status(Status.OK).entity(u.isUsersExist()).build();
	        }
		} catch (Exception e) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response find_Users(@PathParam("id") int id_users) {
		try {
			Users_API users = Users_API.find(id_users);
			if(users == null) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}else {
				return Response.status(Status.OK).entity(users).build();
			}

		} catch (Exception e) {
	        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}